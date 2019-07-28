package com.amit.test;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import com.amit.model.Product;



/**
 * kr.spring.batch.chapter05.test.DummyProductItemWriter
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 8. 1. 오후 2:02
 */
@Component
public class DummyProductItemWriter implements ItemWriter<Product> {

	
	private List<Product> products = new ArrayList<>();

	public void write(List<? extends Product> items) throws Exception {
		products.addAll(items);
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}
}
