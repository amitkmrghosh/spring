package com.javainuse.controller;
 
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.support.ReferenceJobFactory;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
 
@RestController
public class JobInvokerController {
	
	Logger logger = LoggerFactory.getLogger(JobInvokerController.class);
 
    @Autowired
    JobLauncher jobLauncher;
 
    @Autowired
    Job processJob;
 
    @RequestMapping("/invokejob")
    public String handle() throws Exception {
 
            JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis())
                    .toJobParameters();
            jobLauncher.run(processJob, jobParameters);
            return "Batch job has been invoked";
    }
    
    //--------Restart uncompleted Job------------------
    
    @RequestMapping("/restartUncompletedJob")
    public String restartUncompletedJob() throws Exception {
        restartUncompletedJobs();
        return "Restart job has been invoked";
    }
    
    @Autowired
    private JobRepository jobRepository;

    @Autowired
	private JobExplorer jobExplorer;
    
    @Autowired
	private JobOperator jobOperator;
    
    @Autowired
	private JobRegistry jobRegistry;
    
    public void restartUncompletedJobs() {

    	logger.info("Restarting uncompleted jobs");
    	try {
            jobRegistry.register(new ReferenceJobFactory(processJob));

            List<String> jobs = jobExplorer.getJobNames();
            for (String job : jobs) {
                Set<JobExecution> runningJobs = jobExplorer.findRunningJobExecutions(job);

                for (JobExecution runningJob : runningJobs) {
                    runningJob.setStatus(BatchStatus.FAILED);
                    runningJob.setEndTime(new Date());
                    jobRepository.update(runningJob);
                    jobOperator.restart(runningJob.getId());
                    logger.info("Job restarted: " + runningJob);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
    
    //-------Stopping a running Job---------------
    @RequestMapping("/stopJob")
    public String stopJob() throws Exception {
    	stopWithJobOperator();
        return "job has been stopped";
    }
    
    public void stopWithJobOperator() throws Exception {
    	JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis())
                .toJobParameters();
		JobExecution jobExecution = jobLauncher.run(processJob, jobParameters);
		//assertThat(jobExecution.getStatus()).isIn(BatchStatus.STARTING, BatchStatus.STARTED);
		Set<Long> runningExecutions = jobOperator.getRunningExecutions(processJob.getName());
		//assertThat(runningExecutions.size()).isEqualTo(1);

		Long executionId = runningExecutions.iterator().next();
		boolean stopMessageSent = jobOperator.stop(executionId);
		//assertThat(stopMessageSent).isTrue();

		waitForTermination(processJob);
		runningExecutions = jobOperator.getRunningExecutions(processJob.getName());
		//assertThat(runningExecutions.size()).isEqualTo(0);
	}

	private void waitForTermination(Job job) throws NoSuchJobException,
			InterruptedException {
		int timeout = 10000;
		int current = 0;

		while (jobOperator.getRunningExecutions(job.getName()).size() > 0 && current < timeout) {
			Thread.sleep(100);
			current += 100;
		}

		if (jobOperator.getRunningExecutions(job.getName()).size() > 0) {
			throw new IllegalStateException("the execution hasn't stopped " +
					                                "in the expected period (timeout = " + timeout + " ms)." +
					                                "Consider increasing the timeout before checking if it's a bug.");
		}
	}
}