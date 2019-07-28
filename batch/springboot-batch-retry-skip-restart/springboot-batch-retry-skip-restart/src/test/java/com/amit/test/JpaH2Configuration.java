package com.amit.test;

import org.springframework.context.annotation.Configuration;

import com.amit.SkippedProduct;


@Configuration
public class JpaH2Configuration extends H2ConfigBase {

	@Override
	public String[] getMappedPackageNames() {
		return new String[] {
				SkippedProduct.class.getPackage().getName()
		};
	}
}
