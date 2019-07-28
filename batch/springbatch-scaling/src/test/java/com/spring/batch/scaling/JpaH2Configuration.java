package com.spring.batch.scaling;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.spring.batch.scaling.domain.Product;
import com.spring.batch.scaling.repository.ProductRepository;

@Configuration
@EnableJpaRepositories(basePackageClasses = { ProductRepository.class })
public class JpaH2Configuration extends H2ConfigBase {
	@Override
	public String[] getMappedPackageNames() {
		return new String[] {
				Product.class.getPackage().getName()
		};
	}
}
