package com.spring.batch.scaling;

/**
 * com.spring.batch.chapter05.jpa.config.HSqlConfigBase
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 8. 1. 오후 5:10
 */
public abstract class H2ConfigBase extends JpaConfigBase {

	@Override
	public String getDialect() {
		return "org.hibernate.dialect.H2Dialect";
	}
}
