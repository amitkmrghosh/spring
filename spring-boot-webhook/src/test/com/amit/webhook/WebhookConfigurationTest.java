package com.amit.webhook;

import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebhookConfiguration.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"management.port=0"})
public class WebhookConfigurationTest {
	
	private static final Logger logger = LoggerFactory.getLogger(WebhookConfigurationTest.class);
	
	@LocalServerPort
	private int port;
	
	@Value("${local.management.port}")
	private int managementPort;
	
	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void shouldReturn200WhenSendingRequestToController() throws Exception {
		logger.debug("shouldReturn200WhenSendingRequestToController");
		
		ResponseEntity<Object> entity = restTemplate.getForEntity("http://localhost:" + port + "/destinations", 
															      Object.class);
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
	}
	
	@Test
	public void shouldReturn200WhenSendingRequestToManagementEndpoint() throws Exception {
		logger.debug("shouldReturn200WhenSendingRequestToManagementEndpoint");
		
		ResponseEntity<Object> entity = restTemplate.getForEntity("http://localhost:" + managementPort + "/info", 
																  Object.class);
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

}
