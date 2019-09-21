package com.example.springcamel.webservice;

import javax.servlet.http.HttpServletRequest;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.spring.boot.FatJarRouter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.springcamel.model.User;
import com.example.springcamel.service.IntegrationService;

@RestController
@ComponentScan("com")
@ImportResource("classpath:/application-context.xml")
public class IntegrationController extends FatJarRouter{
	
	@Autowired
	private ProducerTemplate producer;
	
	@Autowired
	private CamelContext camelContext;
	
	@Autowired
	IntegrationService integrationService;
	
	final String[] disallowed_fields = new String[] {"details.role", "details.age"};
	
	@RequestMapping(value = "/demoRoute")
	public void demoRoute() {
		System.out.println("Received call");
		producer.sendBody("direct:demoRoute", "Calling /demoroute via rest controller");
	}
	
	
	@PostMapping(value = "/echoMessage", 
			consumes={MediaType.TEXT_PLAIN_VALUE}, 
			produces = {MediaType.TEXT_PLAIN_VALUE})
	@ResponseBody
	public ResponseEntity<?> test(final HttpServletRequest httpRequest, @RequestBody User request) {
		if(request != null) {
			this.integrationService.echoMessage(request);
		}
		return new ResponseEntity<>("Endpoint called successfully", HttpStatus.OK);
	}
}
