package com.amit.advanced;

import org.springframework.batch.support.annotation.Classifier;

import com.amit.model.Product;

public class ProductRouterClassifier {

	@Classifier
	public String classify(Product classifiable) {
		return classifiable.getOperation();
	}
}
