package com.amit.file;

import java.util.Map;

import org.springframework.batch.item.file.transform.LineAggregator;

import com.amit.model.Product;

public class ProductLineAggregator implements LineAggregator<Product> {

	private Map<Class<LineAggregator<Product>>, LineAggregator<Object>> aggregators;

	@Override
	public String aggregate(Product product) {
		return this.aggregators.get(product.getClass()).aggregate(product);
	}

	public void setAggregators(Map<Class<LineAggregator<Product>>, LineAggregator<Object>> aggregators) {
		this.aggregators = aggregators;
	}
}
