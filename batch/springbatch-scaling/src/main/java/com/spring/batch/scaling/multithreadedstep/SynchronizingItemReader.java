package com.spring.batch.scaling.multithreadedstep;

import org.springframework.batch.item.*;

import com.spring.batch.scaling.ThreadUtils;
import com.spring.batch.scaling.domain.Product;

/**
 * com.spring.batch.scaling.multithreadedstep.SynchronizingItemReader
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 8. 19. 오후 11:38
 */
public class SynchronizingItemReader implements ItemReader<Product>, ItemStream {

	
	
	private ItemReader<Product> delegate;

	@Override
	public synchronized Product read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		Product product = delegate.read();
		ThreadUtils.writeThreadExecutionMessage("read", product);
		return product;
	}

	@Override
	public void open(ExecutionContext executionContext) throws ItemStreamException {
		if (this.delegate instanceof ItemStream)
			((ItemStream) this.delegate).open(executionContext);
	}

	@Override
	public void update(ExecutionContext executionContext) throws ItemStreamException {
		if (this.delegate instanceof ItemStream)
			((ItemStream) this.delegate).update(executionContext);
	}

	@Override
	public void close() throws ItemStreamException {
		if (this.delegate instanceof ItemStream)
			((ItemStream) this.delegate).close();
	}

	public ItemReader<Product> getDelegate() {
		return delegate;
	}

	public void setDelegate(ItemReader<Product> delegate) {
		this.delegate = delegate;
	}
}
