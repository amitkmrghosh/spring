package kr.spring.batch.chapter14.batch;

import kr.spring.batch.chapter14.domain.Product;
import kr.spring.batch.chapter14.repository.ProductRepository;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * kr.spring.batch.chapter14.batch.ProductItemWriter
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 8. 18. 오후 9:31
 */
@Transactional
public class ProductItemWriter implements ItemWriter<Product> {

    @Autowired
    
    private ProductRepository productRepository;

    @Override
    public void write(List<? extends Product> items) throws Exception {
        productRepository.save(items);
        productRepository.flush();
    }
}
