package com.amit;

import org.springframework.batch.item.ItemWriter;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class ProductJpaItemWriter implements ItemWriter<Product> {

	@PersistenceContext
	EntityManager em;

	@Override
	public void write(List<? extends Product> items) throws Exception {
		for (Product item : items) {
			if (item != null)
				em.persist(item);
		}
		em.flush();
	}
}
