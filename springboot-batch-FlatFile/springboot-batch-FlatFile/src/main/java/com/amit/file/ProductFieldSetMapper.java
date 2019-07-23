package com.amit.file;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import com.amit.model.Product;

public class ProductFieldSetMapper implements FieldSetMapper<Product> {
	@Override
	public Product mapFieldSet(FieldSet fieldSet) throws BindException {
		Product product = new Product();

		product.setId(fieldSet.readString("id"));
		product.setName(fieldSet.readString("name"));
		product.setDescription(fieldSet.readString("description"));
		product.setPrice(fieldSet.readFloat("price"));

		return product;
	}
}
