package com.spring.batch.scaling;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import com.spring.batch.scaling.domain.BookProduct;
import com.spring.batch.scaling.domain.MobilePhoneProduct;
import com.spring.batch.scaling.domain.Product;

public class ProductFieldSetMapper implements FieldSetMapper<Product> {

    @Override
    public Product mapFieldSet(FieldSet fieldSet) throws BindException {
        String id = fieldSet.readString("id");
        Product product = null;
        if (id.startsWith("PRB")) {
            product = new BookProduct();
        } else if (id.startsWith("PRM")) {
            product = new MobilePhoneProduct();
        } else {
            product = new Product();
        }

        product.setId(id);
        product.setName(fieldSet.readString("name"));
        product.setDescription(fieldSet.readString("description"));
        product.setPrice(fieldSet.readFloat("price"));

        return product;
    }
}
