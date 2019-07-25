package com.amit.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amit.Product;

public interface ProductRepository extends JpaRepository<Product, String> {


}
