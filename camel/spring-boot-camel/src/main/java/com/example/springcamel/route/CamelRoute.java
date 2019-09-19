package com.example.springcamel.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class CamelRoute extends RouteBuilder{

	@Override
	public void configure() throws Exception {
		from("direct:demoRoute")
			.log("Got message: ${body}");
	}

}