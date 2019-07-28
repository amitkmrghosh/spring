package kr.spring.batch.chapter04.test.stop;


import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterRead;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;
import org.springframework.stereotype.Component;

/**
 * kr.spring.batch.chapter04.test.stop.StopListener
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 7. 31. 오후 5:52
 */
@Slf4j
@Component("stopListener")
public class StopListener extends StepExecutionListenerSupport implements ItemReadListener {

    private StepExecution stepExecution;

     private boolean stop = false;

    @Override
    public void beforeStep(StepExecution stepExecution) {
        this.stepExecution = stepExecution;
    }

    @AfterRead
    public void afterRead() { }

    @Override
    public void beforeRead() { }

    @Override
    public void afterRead(Object item) {
        if (stopConditionMet()) {
            log.info("읽기 작업 후에 중단 여부를 판단했습니다. 중단을 요청합니다.");
            stepExecution.setTerminateOnly();
        }
    }

    @Override
    public void onReadError(Exception ex) { }

    private boolean stopConditionMet() {
        return stop;
    }
}
