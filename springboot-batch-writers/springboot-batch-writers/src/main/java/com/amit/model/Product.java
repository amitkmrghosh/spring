package com.amit.model;


import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@DynamicInsert
@DynamicUpdate
public class Product implements Serializable {

	@Id
	private String id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String description;

	@Column(nullable = false)
	private BigDecimal price;

	@Transient
	private transient String operation;

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + "]";
	}

	private static final long serialVersionUID = -4204388747228776515L;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public Product(String id, String name, String description, BigDecimal price, String operation) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.operation = operation;
	}

	public Product() {
		super();
	}
}
