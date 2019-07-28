package com.javainuse.config;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProductPrepareStatementSetter implements ItemPreparedStatementSetter<Product> {
    
	Logger log = LoggerFactory.getLogger(ProductPrepareStatementSetter.class);
	
	@Override
    public void setValues(Product product, PreparedStatement ps) throws SQLException {

        log.debug("Product 정보를 PreparedStatement에 설정합니다. product=[{}]", product);

        ps.setString(1, product.getId());
        ps.setString(2, product.getName());
        ps.setString(3, product.getDescription());
        ps.setFloat(4, product.getPrice());
    }
}
