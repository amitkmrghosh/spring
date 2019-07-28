package com.amit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amit.model.Product;

public interface ProductRepository extends JpaRepository<Product, String> {
}
