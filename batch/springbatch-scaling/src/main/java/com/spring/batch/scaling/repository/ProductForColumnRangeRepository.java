package com.spring.batch.scaling.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.batch.scaling.domain.ProductForColumnRange;

/**
 * com.spring.batch.scaling.repository.ProductForColumnRangeRepository
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 8. 20. 오후 5:19
 */
public interface ProductForColumnRangeRepository extends JpaRepository<ProductForColumnRange, Long> {
}
