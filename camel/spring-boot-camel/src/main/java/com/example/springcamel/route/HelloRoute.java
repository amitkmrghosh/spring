package com.example.springcamel.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import com.example.springcamel.constants.ServiceConstants;
import com.example.springcamel.processor.HelloProcessor;

@Component
public class HelloRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		from(ServiceConstants.HELLO_SERVICE_ENDPOINT)
			.id(ServiceConstants.HELLO_ROUTE_ID)
			.log("I'm in the Camel Route!")
			.process(new HelloProcessor());
	}

}
