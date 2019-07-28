package com.amit.skip;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.listener.SkipListenerSupport;
import org.springframework.batch.item.file.FlatFileParseException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Slf4jSkipListener extends SkipListenerSupport {
	Logger log = LoggerFactory.getLogger(Slf4jSkipListener.class);
	
	@Override
	public void onSkipInRead(Throwable t) {
		if (t instanceof FlatFileParseException) {
			FlatFileParseException ffpe = (FlatFileParseException) t;
			log.error(ffpe.getInput(), t);
		}
	}
}
