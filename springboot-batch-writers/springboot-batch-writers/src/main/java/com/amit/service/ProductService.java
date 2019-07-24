package com.amit.service;

import java.math.BigDecimal;

import com.amit.model.Product;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProductService {

	public void write(Product product) {
		//log.info("기존 서비스 메소드를 사용합니다. writing product id=[{}]", product.getId());
	}

	public void write(String id, String name, String description, BigDecimal price) {
		//log.info("기존 서비스 메소드를 사용합니다. product id=[{}], name=[{}]", id, name);
	}
}
