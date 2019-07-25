package com.amit.retry;


import java.util.List;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class DiscountsWithRetryTemplateTasklet implements Tasklet {

	
	private DiscountService discountService;
	
	private DiscountsHolder discountsHolder;

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

		RetryTemplate retryTemplate = new RetryTemplate();
		SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
		retryPolicy.setMaxAttempts(3);
		retryTemplate.setRetryPolicy(retryPolicy);

		// HINT: 재시도 정책을 지정해서 3번까지 재시도를 수행합니다.
		//
		List<Discount> discounts = retryTemplate.execute(new RetryCallback<List<Discount>, Exception>() {
			@Override
			public List<Discount> doWithRetry(RetryContext context) throws Exception {
				return discountService.getDiscounts();
			}
		});

		discountsHolder.setDiscounts(discounts);
		return RepeatStatus.FINISHED;
	}

	public DiscountService getDiscountService() {
		return discountService;
	}

	public void setDiscountService(DiscountService discountService) {
		this.discountService = discountService;
	}

	public DiscountsHolder getDiscountsHolder() {
		return discountsHolder;
	}

	public void setDiscountsHolder(DiscountsHolder discountsHolder) {
		this.discountsHolder = discountsHolder;
	}
}
