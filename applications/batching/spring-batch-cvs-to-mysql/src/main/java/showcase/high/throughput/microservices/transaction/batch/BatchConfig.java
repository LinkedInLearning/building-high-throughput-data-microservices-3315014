package showcase.high.throughput.microservices.transaction.batch;

import lombok.SneakyThrows;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.RecordFieldSetMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.UrlResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;
import showcase.high.throughput.microservices.domain.Payment;

import javax.sql.DataSource;

/**
 * @author gregory green
 */
@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Value("${batch.file.location}")
    private String fileLocation;

    @Value("${batch.chunk.size}")
    private int chunkSize;

    @Value("${batch.core.pool.size}")
    private int corePoolSize;

    @SneakyThrows
    @Bean
    FlatFileItemReader reader()
    {
       return  new FlatFileItemReaderBuilder<Payment>()
               .name("transactionReader")
               .linesToSkip(1) //skip header
               .delimited()
               .names(new String[]{"id","details","contact","location","amount","timestamp"})
               .fieldSetMapper(new RecordFieldSetMapper<Payment>(Payment.class))
               .resource(new UrlResource(fileLocation))
               .build();
    }

    @Bean
    public JdbcBatchItemWriter<Payment> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Payment>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("""
                INSERT INTO batching.payments (id, 
                details, contact,  
                location, amount, timestamp) 
                VALUES (:id, :details, :contact, :location, :amount,
                :timestamp)
                """)
                .dataSource(dataSource)
                .build();
    }

    @Bean
    public Job importUserJob(
            JobRepository jobRepository,
                             JobExecutionListener listener, Step step1) {
        return new JobBuilder("importTransactions")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .repository(jobRepository)
                .flow(step1)
                .end()
                .build();
    }

    @Bean
    JobExecutionListener listener(JdbcTemplate jdbcTemplate)
    {
        return new JobExecutionListener() {
            @Override
            public void beforeJob(JobExecution jobExecution) {
                jdbcTemplate.update("truncate  batching.payments");
            }

            @Override
            public void afterJob(JobExecution jobExecution) {
            }
        };
    }
    @Bean
    public Step step1(ItemWriter<Payment> writer,
                      JobRepository jobRepository,
                      PlatformTransactionManager transactionManager,
                      ThreadPoolTaskExecutor taskExecutor) {

        taskExecutor.setCorePoolSize(corePoolSize);

        return new StepBuilder("step1")
                .transactionManager(transactionManager)
                .repository(jobRepository)
                .<Payment, Payment> chunk(chunkSize)
                .reader(reader())
                .writer(writer)
                .taskExecutor(taskExecutor)
                .build();
    }

}
