package com.amit.test.beans;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DummyItemProcessor implements ItemProcessor<String, String> {
	Logger log = LoggerFactory.getLogger(DummyItemProcessor.class);
	private BusinessService service;

	public DummyItemProcessor(BusinessService service) {
		this.service = service;
	}

	@Override
	public String process(String item) throws Exception {
		log.debug("processing [{}]", item);
		service.processing(item);
		log.debug("after processing [{}]", item);
		return item;
	}
}
