package com.amit.retry;


import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.util.List;

public class DiscountsTasklet implements Tasklet {

	 private DiscountService discountService;
	 private DiscountsHolder discountsHolder;

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		List<Discount> discounts = discountService.getDiscounts();
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
