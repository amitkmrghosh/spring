package com.javainuse.config;

import lombok.Data;

import java.io.Serializable;


@Data
public class Product implements Serializable {

    private String id;
    private String name;
    private String description;
    private float price;

    private static final long serialVersionUID = 3552827527136677964L;

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

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}
}
