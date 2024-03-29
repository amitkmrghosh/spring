package kr.spring.batch.chapter13;

import kr.spring.batch.chapter13.domain.Product;


import org.springframework.batch.item.*;

/**
 * kr.spring.batch.chapter13.WrappedItemReader
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 8. 20. 오후 2:08
 */
public class WrappedItemReader implements ItemReader<Product>, ItemStream {

    
    
    private ItemReader<Product> delegate;

    @Override
    public Product read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        Product product = delegate.read();
        ThreadUtils.writeThreadExecutionMessage("read", product);
        return product;
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        if (this.delegate instanceof ItemStream) {
            ((ItemStream) this.delegate).open(executionContext);
        }
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
        if (this.delegate instanceof ItemStream) {
            ((ItemStream) this.delegate).update(executionContext);
        }
    }

    @Override
    public void close() throws ItemStreamException {
        if (this.delegate instanceof ItemStream) {
            ((ItemStream) this.delegate).close();
        }
    }
}
