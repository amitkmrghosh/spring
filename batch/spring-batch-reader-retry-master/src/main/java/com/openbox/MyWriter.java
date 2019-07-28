package com.openbox;

import java.util.List;

import org.springframework.batch.item.ItemWriter;

public class MyWriter implements ItemWriter<String> {

	@Override
	public void write(final List<? extends String> arg0) throws Exception {
		System.out.println("MyWriter : " + arg0);
	}

}
