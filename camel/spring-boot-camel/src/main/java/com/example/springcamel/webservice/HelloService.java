package com.example.springcamel.webservice;

import javax.servlet.http.HttpServletRequest;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.ExchangeBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.springcamel.constants.ServiceConstants;

@Controller
@RequestMapping("service")
public class HelloService {
	
	@Autowired
	private ProducerTemplate producer;
	
	@Autowired
	private CamelContext camelContext;
	
	@PostMapping(value = "/hello", consumes={MediaType.TEXT_PLAIN_VALUE}, produces = {MediaType.TEXT_PLAIN_VALUE})
	@ResponseBody
	public ResponseEntity<?> hello(final HttpServletRequest request, @RequestBody String requestBody) {
		final Exchange requestExchange = ExchangeBuilder.anExchange(camelContext).withBody(requestBody).build();
		final Exchange responseExchange = producer.send(ServiceConstants.HELLO_SERVICE_ENDPOINT, requestExchange);
		final String responseBody = responseExchange.getOut().getBody(String.class);
		final int responseCode = responseExchange.getOut().getHeader(Exchange.HTTP_RESPONSE_CODE, Integer.class).intValue();
		return ResponseEntity.status(responseCode).body(responseBody);
	}
}
