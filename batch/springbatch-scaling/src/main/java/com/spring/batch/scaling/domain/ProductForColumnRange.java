package com.spring.batch.scaling.domain;



import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * com.spring.batch.scaling.domain.ProductForColumnRange
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 8. 19. 오후 11:24
 */
@Entity
@DynamicInsert
@DynamicUpdate


public class ProductForColumnRange implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String description;

    private float price;

    @Override
    public String toString() {
        return "ProductForColumnRange# id=[" + id + "], name=[" + name + "]";
    }

    private static final long serialVersionUID = -4686885077293578413L;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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
