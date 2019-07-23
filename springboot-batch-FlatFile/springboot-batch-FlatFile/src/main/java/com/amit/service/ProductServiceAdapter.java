package com.amit.service;

import java.util.List;

import org.springframework.beans.factory.InitializingBean;

import com.amit.model.Product;

public class ProductServiceAdapter implements InitializingBean {

	private ProductService productService;

	private List<Product> products;

	@Override
	public void afterPropertiesSet() throws Exception {
		assert productService != null;
		this.products = productService.getProducts();
	}

	public Product getProduct() {
		return (products.size() > 0) ? products.remove(0) : null;
	}

	public ProductService getProductService() {
		return productService;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}
}
