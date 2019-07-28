package com.amit.model;

public class MobilePhoneProduct extends Product {

	private String manufacturer;

	private static final long serialVersionUID = 501496932942144935L;

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
}
