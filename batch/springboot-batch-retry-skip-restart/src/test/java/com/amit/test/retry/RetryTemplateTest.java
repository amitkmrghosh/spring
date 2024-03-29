package com.amit.test.retry;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.TransientDataAccessResourceException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.amit.retry.Discount;
import com.amit.retry.DiscountService;
import com.amit.retry.DiscountsHolder;
import com.amit.retry.DiscountsTasklet;
import com.amit.retry.DiscountsWithRetryTemplateTasklet;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RetryTemplateConfiguration.class })
public class RetryTemplateTest {

	@Test
	public void discountTaskletWithRetryTemplateOk() throws Exception {
		DiscountsWithRetryTemplateTasklet tasklet = new DiscountsWithRetryTemplateTasklet();
		DiscountService service = mock(DiscountService.class);
		DiscountsHolder holder = new DiscountsHolder();
		tasklet.setDiscountService(service);
		tasklet.setDiscountsHolder(holder);

		List<Discount> discounts = new ArrayList<Discount>();
		discounts.add(new Discount());

		// HINT: Mockito를 이용하여, 두번의 실퍠 후 성공하는 가상 시나리오를 제작합니다.
		when(service.getDiscounts())
				.thenThrow(new TransientDataAccessResourceException(""))
				.thenThrow(new TransientDataAccessResourceException(""))
				.thenReturn(discounts);

		RepeatStatus status = tasklet.execute(null, null);
		Assertions.assertThat(status).isEqualTo(RepeatStatus.FINISHED);
		Assertions.assertThat(holder.getDiscounts()).isEqualTo(discounts);
	}

	@Test(expected = TransientDataAccessResourceException.class)
	public void discountTaskletWithRetryTemplateRetryExhausted() throws Exception {
		DiscountsWithRetryTemplateTasklet tasklet = new DiscountsWithRetryTemplateTasklet();
		DiscountService service = mock(DiscountService.class);
		DiscountsHolder holder = new DiscountsHolder();
		tasklet.setDiscountService(service);
		tasklet.setDiscountsHolder(holder);

		List<Discount> discounts = new ArrayList<Discount>();
		discounts.add(new Discount());

		when(service.getDiscounts())
				.thenThrow(new TransientDataAccessResourceException(""))
				.thenThrow(new TransientDataAccessResourceException(""))
				.thenThrow(new TransientDataAccessResourceException(""))
				.thenReturn(discounts);

		tasklet.execute(null, null);
	}

	@Autowired
	private DiscountsTasklet discountsTasklet;

	@Autowired
	private DiscountService discountService;

	@Autowired
	private DiscountsHolder discountsHolder;

	@Test
	public void transparentRetry() throws Exception {
		List<Discount> discounts = new ArrayList<Discount>();
		discounts.add(new Discount());

		when(discountService.getDiscounts())
				.thenThrow(new TransientDataAccessResourceException(""))
				.thenReturn(discounts)
				.thenThrow(new TransientDataAccessResourceException(""))
				.thenThrow(new TransientDataAccessResourceException(""))
				.thenReturn(discounts);

		RepeatStatus status = discountsTasklet.execute(null, null);
		Assertions.assertThat(status).isEqualTo(RepeatStatus.FINISHED);
		Assertions.assertThat(discountsHolder.getDiscounts()).isSameAs(discounts);

		try {
			discountsTasklet.execute(null, null);
			Assert.fail();

		} catch (TransientDataAccessResourceException e) {
			// OK
		}
	}
}
