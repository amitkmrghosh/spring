package com.openbox;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.retry.annotation.Retryable;

public class MyReader implements ItemReader<String> {

	private long count;
	private long retryCount;

	@Override
	@Retryable(include = { MyException.class }, maxAttempts = 5)
	public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		final long value = count;
		System.out.println("MyReader : " + value);

		if (value == 3 && retryCount <= 2) {
			retryCount++;
			System.out.println("****");
			Thread.sleep(500);
			throw new MyException();
		}

		if (value < 5) {
			count++;
			Thread.sleep(500);
			return String.valueOf(value);
		} else {
			return null;
		}
	}

}
