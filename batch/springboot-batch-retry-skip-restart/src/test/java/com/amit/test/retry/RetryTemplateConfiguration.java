package com.amit.test.retry;

import org.mockito.Mockito;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.retry.interceptor.RetryOperationsInterceptor;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import com.amit.jpa.repositories.ProductRepository;
import com.amit.retry.DiscountService;
import com.amit.retry.DiscountsHolder;
import com.amit.retry.DiscountsTasklet;
import com.amit.test.JpaH2Configuration;

/**
 * kr.spring.batch.chapter08.test.retry.RetryTemplateConfiguration
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 8. 10. 오후 5:03
 */
@Configuration
@EnableAspectJAutoProxy
@EnableBatchProcessing
@EnableJpaRepositories(basePackageClasses = { ProductRepository.class })
@Import({ JpaH2Configuration.class })
// NOTE: 현재로는 aop:advisor 연결을 java config로 표현할 방법이 없습니다. xml configuration을 참고하세요.
// 참고: http://stackoverflow.com/questions/14068525/javaconfig-replacing-aopadvisor-and-txadvice
@ImportResource(value = { "classpath:retry/RetryTemplateAop.xml" })
public class RetryTemplateConfiguration {

	@Bean
	public RetryOperationsInterceptor retryAdvice() {

		SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
		retryPolicy.setMaxAttempts(2);

		RetryTemplate retryTemplate = new RetryTemplate();
		retryTemplate.setRetryPolicy(retryPolicy);

		RetryOperationsInterceptor interceptor = new RetryOperationsInterceptor();
		interceptor.setRetryOperations(retryTemplate);

		return interceptor;
	}

	@Bean
	public DiscountsTasklet discountsTasklet() {
		DiscountsTasklet tasklet = new DiscountsTasklet();
		tasklet.setDiscountService(discountService());
		tasklet.setDiscountsHolder(discountsHolder());
		return tasklet;
	}

	@Bean
	public DiscountService discountService() {
		DiscountService service = Mockito.mock(DiscountService.class);
		return service;
	}

	@Bean
	public DiscountsHolder discountsHolder() {
		return new DiscountsHolder();
	}
}
