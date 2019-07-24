package com.amit.step;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.amit.model.Product;
import com.amit.repository.ProductRepository;

@Transactional
public class InsertProductItemWriter implements ItemWriter<Product> {

	@Autowired
	ProductRepository repository;

	@Override
	public void write(List<? extends Product> items) throws Exception {
		repository.save(items);
		repository.flush();
	}
}
