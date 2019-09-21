/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.processor.aggregate.jdbc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.junit.Test;

public class JdbcAggregateRecoverWithRedeliveryPolicyTest extends AbstractJdbcAggregationTestSupport {

    private static AtomicInteger counter = new AtomicInteger(0);
    
    @Override
    void configureJdbcAggregationRepository() {
        // enable recovery
        repo.setUseRecovery(true);
        // check faster
        repo.setRecoveryInterval(500, TimeUnit.MILLISECONDS); 
    }

    @Test
    public void testJdbcAggregateRecover() throws Exception {
        getMockEndpoint("mock:aggregated").setResultWaitTime(20000);
        getMockEndpoint("mock:result").setResultWaitTime(20000);

        // should fail the first 3 times and then recover
        getMockEndpoint("mock:aggregated").expectedMessageCount(4);
        getMockEndpoint("mock:result").expectedBodiesReceived("ABCDE");
        // should be marked as redelivered
        getMockEndpoint("mock:result").message(0).header(Exchange.REDELIVERED).isEqualTo(Boolean.TRUE);
        // on the 2nd redelivery attempt we success
        getMockEndpoint("mock:result").message(0).header(Exchange.REDELIVERY_COUNTER).isEqualTo(3);

        template.sendBodyAndHeader("direct:start", "A", "id", 123);
        template.sendBodyAndHeader("direct:start", "B", "id", 123);
        template.sendBodyAndHeader("direct:start", "C", "id", 123);
        template.sendBodyAndHeader("direct:start", "D", "id", 123);
        template.sendBodyAndHeader("direct:start", "E", "id", 123);

        assertMockEndpointsSatisfied(30, TimeUnit.SECONDS);
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("direct:start")
                        .aggregate(header("id"), new MyAggregationStrategy())
                        .completionSize(5).aggregationRepository(repo)
                        // this is the output from the aggregator
                        .log("aggregated exchange id ${exchangeId} with ${body}")
                        .to("mock:aggregated")
                                // simulate errors the first three times
                        .process(new Processor() {
                            public void process(Exchange exchange) throws Exception {
                                int count = counter.incrementAndGet();
                                if (count <= 3) {
                                    throw new IllegalArgumentException("Damn");
                                }
                            }
                        })
                        .to("mock:result")
                        .end();
            }
        };
    }
}