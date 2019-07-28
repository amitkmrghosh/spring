package kr.spring.batch.chapter01.batch;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.zip.ZipInputStream;

/**
 * kr.experiments.springbatch.chapter01.batch.DecompressTasklet
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 7. 30. 오후 2:50
 */
@Slf4j
public class DecompressTasklet implements Tasklet {

     private Resource inputResource;
     private String targetDirectory;
     private String targetFile;

    private void setParameters(JobParameters jobParameters) {

        if (inputResource == null)
            inputResource = new ClassPathResource((String) jobParameters.getString("inputResource"));

        if (StringUtils.isEmpty(targetDirectory))
            targetDirectory = jobParameters.getString("targetDirectory");

        if (StringUtils.isEmpty(targetFile))
            targetFile = jobParameters.getString("targetFile");
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        if (chunkContext != null)
            setParameters(chunkContext.getStepContext().getStepExecution().getJobParameters());

        ZipInputStream zis = new ZipInputStream(new BufferedInputStream(inputResource.getInputStream()));

        File targetDirectoryAsFile = new File(targetDirectory);
        if (!targetDirectoryAsFile.exists()) {
            FileUtils.forceMkdir(targetDirectoryAsFile);
        }

        File target = new File(targetDirectory, targetFile);

        BufferedOutputStream dest = null;
        while (zis.getNextEntry() != null) {
            if (!target.exists()) {
                target.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(target);
            dest = new BufferedOutputStream(fos);
            IOUtils.copy(zis, dest);
            dest.flush();
            dest.close();
        }
        zis.close();

        if (!target.exists()) {
            throw new IllegalStateException("Could not decompress anything from the archive!");
        }

        return RepeatStatus.FINISHED;
    }
}
