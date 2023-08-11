package showcase.high.throughput.microservices.order.inventory;


import com.rabbitmq.stream.Environment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionNameStrategy;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class RabbitConfig {

    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${spring.rabbitmq.username:guest}")
    private String username = "guest";

    @Value("${spring.rabbitmq.password:guest}")
    private String password   = "guest";

    @Value("${spring.rabbitmq.host:127.0.0.1}")
    private String hostname = "127.0.0.1";

    @Value("${spring.rabbitmq.stream.name}")
    private String streamName;

    @Bean
    ConnectionNameStrategy connectionNameStrategy(){
        return (connectionFactory) -> applicationName;
    }


    @Bean
    public MessageConverter converter()
    {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    Environment rabbitStreamEnvironment() {

        var env = Environment.builder()
                .host(hostname)
                .username(username)
                .password(password)
                .clientProperty("id",applicationName)
                .build();

//        env.streamCreator().stream(streamName).create();

        return env;
    }

    @Bean
    Queue stream() {
        return QueueBuilder.durable(streamName)
                .stream()
                .build();
    }

//    @Bean
//    @ConditionalOnProperty(name = "rabbitmq.streaming.replay",havingValue = "true")
//    ListenerContainerCustomizer<MessageListenerContainer> customizer() {
//        return ( MessageListenerContainer cont, String dest, String group) ->
//        {
//            if(!(cont instanceof StreamListenerContainer))
//                return;
//
//            final StreamListenerContainer container = StreamListenerContainer.class.cast(cont);
//
//            log.info("Replaying, setting offset to first the record for streams");
//            container.setConsumerCustomizer( (name, builder) -> {
//                builder.offset(OffsetSpecification.first());
//            });
//        };
//    }

}