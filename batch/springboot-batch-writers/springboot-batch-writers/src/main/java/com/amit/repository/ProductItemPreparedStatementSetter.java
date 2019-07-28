package com.amit.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.batch.item.database.ItemPreparedStatementSetter;

import com.amit.model.Product;

public class ProductItemPreparedStatementSetter implements ItemPreparedStatementSetter<Product> {
	@Override
	public void setValues(Product item, PreparedStatement ps) throws SQLException {
		ps.setString(1, item.getId());
		ps.setString(2, item.getName());
		ps.setBigDecimal(3, item.getPrice());
	}
}
