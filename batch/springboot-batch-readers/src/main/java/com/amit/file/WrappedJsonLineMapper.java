package com.amit.file;

import java.util.Map;

import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.JsonLineMapper;

import com.amit.model.Product;

public class WrappedJsonLineMapper implements LineMapper<Product> {

	private JsonLineMapper delegate;

	@Override
	public Product mapLine(String line, int lineNumber) throws Exception {

		Map<String, Object> productAsMap = delegate.mapLine(line, lineNumber);

		Product product = new Product();
		product.setId((String) productAsMap.get("id"));
		product.setName((String) productAsMap.get("name"));
		product.setDescription((String) productAsMap.get("description"));
		product.setPrice(Float.parseFloat(productAsMap.get("price").toString()));

		return product;
	}

	public JsonLineMapper getDelegate() {
		return delegate;
	}

	public void setDelegate(JsonLineMapper delegate) {
		this.delegate = delegate;
	}
}
