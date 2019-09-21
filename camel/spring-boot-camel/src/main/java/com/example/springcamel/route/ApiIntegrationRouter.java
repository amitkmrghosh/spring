package com.example.springcamel.route;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.example.springcamel.model.User;
import com.example.springcamel.processor.HelloProcessor;

@Component("apiRouter")
public class ApiIntegrationRouter extends RouteBuilder {
	
	private final Logger LOG = LoggerFactory.getLogger(ApiIntegrationRouter.class);

	@Override
	public void configure() throws Exception {
		from("direct:demoRoute")
			.log("Got Message {body}")
			.process(new HelloProcessor());
		
		from("direct:ECHOMSG")
			.log("Got Message {body}")
			.process(new Processor() {
				@Override
				public void process(Exchange exchange) {
					LOG.info("Processor -" 
							+ exchange.getExchangeId() + "-BODY-"
							+ exchange.getIn().getBody() + " Timestamp:-"
							+ System.currentTimeMillis());
					try {
						JAXBContext jaxbContext = JAXBContext.newInstance(User.class);
						Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
						jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
						jaxbMarshaller.marshal(exchange.getIn().getBody(), System.out);
					} catch(Exception e) {
						e.printStackTrace();
					}
				}
			});
	}
}
