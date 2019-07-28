package com.spring.batch.scaling.domain;



import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "productType")
@DiscriminatorValue("Product")
@DynamicInsert
@DynamicUpdate


public class Product implements Serializable {

    public Product() {}

    public Product(String id) {
        this.id = id;
    }

    @Id
    private String id;

    private String name;

    private String description;

    private float price;

    private boolean processed;

    @Override
    public String toString() {
        return "Product# id=[" + id + "], name=[" + name + "]";
    }

    private static final long serialVersionUID = 7647304969032217912L;

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

	public boolean isProcessed() {
		return processed;
	}

	public void setProcessed(boolean processed) {
		this.processed = processed;
	}
}
