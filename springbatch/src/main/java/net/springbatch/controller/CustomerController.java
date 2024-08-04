package net.springbatch.controller;

import lombok.AllArgsConstructor;
import net.springbatch.model.Customer;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class CustomerController {
    private JobLauncher jobLauncher;
    private Job job;

    @PostMapping("springbatch/job")
    public void importCsvToDB() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("start-csv", System.currentTimeMillis()).toJobParameters();
        jobLauncher.run(job,jobParameters);
    }

}
