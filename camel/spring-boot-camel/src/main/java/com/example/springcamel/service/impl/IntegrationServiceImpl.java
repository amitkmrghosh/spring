package com.example.springcamel.service.impl;

import org.apache.camel.ProducerTemplate;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springcamel.model.User;
import com.example.springcamel.route.ApiIntegrationRouter;
import com.example.springcamel.service.IntegrationService;

@Service
public class IntegrationServiceImpl implements IntegrationService {

	private final Logger LOG = LoggerFactory.getLogger(ApiIntegrationRouter.class);
	
	@Autowired
	private ProducerTemplate producerTemplate;
	
	@Override
	public void echoMessage(User user) {
		StopWatch watch = new StopWatch();
		watch.start();
		producerTemplate.sendBody("direct:ECHOMSG", user);
		watch.stop();
		LOG.info("Took {} miliseconds", watch.getTime());
		watch.reset();
	}
}
