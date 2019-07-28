package com.spring.batch.scaling.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.spring.batch.scaling.domain.Product;

/**
 * com.spring.batch.scaling.repository.ProductRepository
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 8. 19. 오후 11:32
 */
public interface ProductRepository extends JpaRepository<Product, String> {

    @Query("select count(p.id) from Product p where p.processed = false")
    long countByNotProcessed();

    @Query("select p from Product p where p.processed = false")
    List<Product> findByNotProcessed();
}
