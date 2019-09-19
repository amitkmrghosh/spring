package com.example.springcamel.route;

import org.apache.camel.ExchangePattern;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import com.example.springcamel.constants.ServiceConstants;
import com.example.springcamel.processor.RedeliveryProcessor;


@Component
public class QueueReaderRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {

		from("jms:deadLetterQueue")
			.routeId(ServiceConstants.QUEUE_READER_ROUTE_ID)
			.process(new RedeliveryProcessor())
			.to(ExchangePattern.InOnly, "jms:destinationQueue")
			.end();
	}

}
