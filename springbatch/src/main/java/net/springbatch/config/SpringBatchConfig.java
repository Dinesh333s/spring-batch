package net.springbatch.config;

import lombok.AllArgsConstructor;
import net.springbatch.model.Customer;
import net.springbatch.repo.CustomerRepo;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.BeanWrapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

//@EnableBatchProcessing  removed
@Configuration
@AllArgsConstructor
public class SpringBatchConfig {

    //Jobbuilderfactory and stepbuilderfactory deprecated
    private JobRepository jobRepository;
    private PlatformTransactionManager platformTransactionManager;
    private CustomerRepo customerRepo;

    //reader
    @Bean
    public FlatFileItemReader<Customer> flatFileItemReaderBuilder() {

        FlatFileItemReader<Customer> fileItemReaderBuilder = new FlatFileItemReader<>();
        fileItemReaderBuilder.setResource(new FileSystemResource("src/main/resources/customers.csv"));
        fileItemReaderBuilder.setName("csv-reader");
        fileItemReaderBuilder.setLinesToSkip(1);
        fileItemReaderBuilder.setLineMapper(linemapper());
        return fileItemReaderBuilder;
    }

    private LineMapper<Customer> linemapper() {
        DefaultLineMapper<Customer> defaultLineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
        delimitedLineTokenizer.setDelimiter(",");
        delimitedLineTokenizer.setStrict(false);
        delimitedLineTokenizer.setNames("id","firstName","lastName","email","gender","contactNo","country","dob");
        BeanWrapperFieldSetMapper<Customer> beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<>();
        beanWrapperFieldSetMapper.setTargetType(Customer.class);
        defaultLineMapper.setLineTokenizer(delimitedLineTokenizer);
        defaultLineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);
        return defaultLineMapper;
    }

    //processor
    public CustomerProcessor processor() {
        return new CustomerProcessor();
    }

    //writer
    @Bean
    public RepositoryItemWriter<Customer> repositoryItemWriter() {
        RepositoryItemWriter<Customer> repositoryItemWriter = new RepositoryItemWriter<>();
        repositoryItemWriter.setRepository(customerRepo);
        repositoryItemWriter.setMethodName("save");
        return repositoryItemWriter;
    }

    //step
    @Bean
    public Step step1() {
        return new StepBuilder("csv-step", jobRepository)
                    .<Customer, Customer>chunk(10, platformTransactionManager)
                .reader(flatFileItemReaderBuilder())
                .processor(processor())
                .writer(repositoryItemWriter())
                .build();
    }

    //job
    @Bean
    public Job runjob()
    {
        return new JobBuilder("customers-job", jobRepository)
                .start(step1())
                .build();
    }
}
