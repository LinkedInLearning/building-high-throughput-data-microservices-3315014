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
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.RecordFieldSetMapper;
import org.springframework.batch.item.kafka.KafkaItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.UrlResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.converter.JsonMessageConverter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;
import showcase.high.throughput.microservices.domain.Payment;

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
    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${batch.apache.kafka.topic.name:test-transactions}")
    private String topicName;

    @SneakyThrows
    @Bean
    FlatFileItemReader<Payment> reader()
    {
       return  new FlatFileItemReaderBuilder<Payment>()
               .name(applicationName+"-reader")
               .linesToSkip(1) //skip header
               .delimited()
               .names(new String[]{"id","details","contact","location","amount","timestamp"})
               .fieldSetMapper(new RecordFieldSetMapper<Payment>(Payment.class))
               .resource(new UrlResource(fileLocation))
               .build();
    }


    @Bean
    public Job importUserJob(
            JobRepository jobRepository,
                             JobExecutionListener listener, Step step1) {
        return new JobBuilder(applicationName)
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .repository(jobRepository)
                .flow(step1)
                .end()
                .build();
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

    @Bean
    KafkaItemWriter<String, Payment> writer(KafkaTemplate<String, Payment> template)
    {
        template.setMessageConverter(new JsonMessageConverter());
        template.setDefaultTopic(topicName);

        var writer = new KafkaItemWriter<String, Payment>();
        writer.setKafkaTemplate(template);
        writer.setItemKeyMapper(transaction -> transaction.id() );
        return writer;
    }

}
