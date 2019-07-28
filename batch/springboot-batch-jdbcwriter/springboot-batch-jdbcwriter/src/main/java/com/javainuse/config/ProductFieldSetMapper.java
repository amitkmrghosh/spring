package com.javainuse.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ProductFieldSetMapper implements FieldSetMapper<Product> {

	Logger logger = LoggerFactory.getLogger(ProductFieldSetMapper.class);

    @Override
    public Product mapFieldSet(FieldSet fieldSet) throws BindException {
        // Product product = mapper.map(fieldSet, Product.class);

        Product product = new Product();
        product.setId(fieldSet.readString("id"));
        product.setName(fieldSet.readString("name"));
        product.setDescription(fieldSet.readString("description"));
        product.setPrice(fieldSet.readFloat("price"));

        logger.info("Mapped Product=[{}]", product);

        return product;
    }
}
