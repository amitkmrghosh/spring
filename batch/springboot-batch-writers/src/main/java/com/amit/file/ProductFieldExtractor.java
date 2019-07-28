package com.amit.file;

import java.math.BigDecimal;

import org.springframework.batch.item.file.transform.FieldExtractor;

import com.amit.model.Product;

public class ProductFieldExtractor implements FieldExtractor<Product> {
	@Override
	public Object[] extract(Product item) {
		return new Object[] {
				"BEGIN",
				item.getId(),
				item.getPrice(),
				item.getPrice().multiply(new BigDecimal("0.15")),
				item.getName(),
				"END"
		};
	}
}
