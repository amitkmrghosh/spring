package com.amit.retry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.listener.RetryListenerSupport;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class Slf4jRetryListener extends RetryListenerSupport {
	Logger log = LoggerFactory.getLogger(Slf4jRetryListener.class);
	@Override
	public <T, E extends Throwable> void onError(RetryContext context, RetryCallback<T, E> callback, Throwable throwable) {
		log.error("retried operation", throwable);
	}
}
