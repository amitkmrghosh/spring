package com.example.springcamel.webservice;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CamelController {
	
	@Autowired
	CamelContext camelContext;
	
	@Autowired
	ProducerTemplate producerTemplate;

	@RequestMapping(value = "/camel")
	public void startCamel() {
                System.out.println("Received call!");  
		producerTemplate.sendBody("direct:demoRoute", "Calling via Spring Boot Rest Controller");
	}
	
 
}