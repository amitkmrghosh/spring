package com.spring.batch.scaling.domain;




import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * com.spring.batch.scaling.domain.BookProduct
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 8. 19. 오후 11:26
 */
@Entity
@DiscriminatorValue("MobilePhone")


public class BookProduct extends Product {

	private String publisher;

	private static final long serialVersionUID = -6363476647920249030L;

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
}
