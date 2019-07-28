package kr.spring.batch.chapter10.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;

/**
 * kr.spring.batch.chapter10.tasklet.TrackImportFromJobContextTasklet
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 8. 16. 오후 3:17
 */
public class TrackImportFromJobContextTasklet extends AbstractBatchServiceTasklet {

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        ExecutionContext executionContext = getJobExecutionContext(chunkContext);
        String importId = executionContext.getString("importId");
        getBatchService().track(importId);
        return RepeatStatus.FINISHED;
    }
}
