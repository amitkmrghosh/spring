package com.amit.test;


import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.amit.model.Product;

@RunWith(SpringJUnit4ClassRunner.class)
public abstract class AbstractJobStructureTest {

	@Autowired
	protected Job job;

	@Autowired
	protected JobLauncher jobLauncher;

	@Autowired
	protected DummyProductItemWriter writer;

	protected Product createProduct(String id, String name, String description, float price) {
		return new Product(id, name, description, price);
	}

	protected void hasProduct(List<Product> products, String productId) {
		for (Product product : products) {
			if (product.getId().equals(productId))
				return;
		}

		Assert.fail("Product with id " + productId + " is expected.");
	}

	protected void checkProducts(List<Product> products, String[] productIds) {
		Assertions.assertThat(products.size()).isEqualTo(8);
		for (String productId : productIds) {
			hasProduct(products, productId);
		}
	}
}
