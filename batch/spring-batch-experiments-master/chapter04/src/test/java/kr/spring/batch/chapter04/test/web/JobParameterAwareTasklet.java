package kr.spring.batch.chapter04.test.web;



import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.util.Map;

/**
 * kr.spring.batch.chapter04.test.web.JobParameterAwareTasklet
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 7. 31. 오후 6:45
 */
public class JobParameterAwareTasklet implements Tasklet {

    
    
    private Map<String, String> params;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        Map<String, Object> jobParams = chunkContext.getStepContext().getJobParameters();

        this.params.clear();
        for (Map.Entry<String, Object> entry : jobParams.entrySet()) {
            this.params.put(entry.getKey(), entry.getValue().toString());
        }

        return RepeatStatus.FINISHED;
    }
}
