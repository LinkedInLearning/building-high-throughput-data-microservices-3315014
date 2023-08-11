package showcase.high.throughput.microservices.transaction.rabbit.stream.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.stream.Environment;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.rabbit.stream.producer.RabbitStreamTemplate;
import showcase.high.throughput.microservices.transaction.batch.mapping.TransactionToJsonBytesConverter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Configuration
@ComponentScan(basePackageClasses = TransactionToJsonBytesConverter.class)
public class RabbitConfig {

    @Value("${rabbitmq.stream.name:transaction}")
    private String streamName;


    @Primary
    @Bean
    ObjectMapper objectMapper()
    {
        ObjectMapper mapper = new ObjectMapper();
        // StdDateFormat is ISO8601 since jackson 2.9
        String pattern = "yyyy-MM-dd HH:mm:ss.SSSSS";
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        mapper.setDateFormat(dateFormat);

        return mapper;
    }

    @Bean
    Queue queueStream()
    {
        return QueueBuilder.durable(streamName).stream().build();
    }


    @Bean
    RabbitStreamTemplate streamTemplate(Environment env, MessageConverter messageConverter) {
        var template = new RabbitStreamTemplate(env, streamName);
        template.setProducerCustomizer((name, builder) -> builder.name("test"));

        template.setMessageConverter(messageConverter);
        return template;
    }

    @Bean
    MessageConverter messageConverter()
    {
        return new Jackson2JsonMessageConverter();
    }

}
