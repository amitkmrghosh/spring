package com.amit.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amit.SkippedProduct;

public interface SkippedProductRepository extends JpaRepository<SkippedProduct, Long> {
}
