package com.spring.batch.scaling.domain;




import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * com.spring.batch.scaling.domain.MobilePhoneProduct
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 8. 19. 오후 11:27
 */
@Entity
@DiscriminatorValue("MobilePhone")


public class MobilePhoneProduct extends Product {

	private String manufacturer;

	private static final long serialVersionUID = -5886631781029655115L;

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
}
