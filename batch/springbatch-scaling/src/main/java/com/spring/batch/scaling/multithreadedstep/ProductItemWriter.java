package com.spring.batch.scaling.multithreadedstep;

import org.springframework.batch.item.ItemWriter;
import org.springframework.transaction.annotation.Transactional;

import com.spring.batch.scaling.ThreadUtils;
import com.spring.batch.scaling.domain.Product;
import com.spring.batch.scaling.repository.ProductRepository;

import java.util.List;

@Transactional
public class ProductItemWriter implements ItemWriter<Product> {

    
    ProductRepository productRepository;

    @Override
    public void write(List<? extends Product> items) throws Exception {
        ThreadUtils.writeThreadExecutionMessage("write", items);
        for (Product item : items) {
            item.setProcessed(true);
            productRepository.save(item);
        }
        productRepository.flush();
    }

	public ProductRepository getProductRepository() {
		return productRepository;
	}

	public void setProductRepository(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}
}
