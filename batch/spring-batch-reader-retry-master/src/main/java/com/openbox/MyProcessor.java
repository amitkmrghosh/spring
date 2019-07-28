package com.openbox;

import org.springframework.batch.item.ItemProcessor;

public class MyProcessor implements ItemProcessor<String, String> {

	@Override
	public String process(final String arg0) throws Exception {
		System.out.println("MyProcessor : " + arg0);
		return arg0;
	}

}
