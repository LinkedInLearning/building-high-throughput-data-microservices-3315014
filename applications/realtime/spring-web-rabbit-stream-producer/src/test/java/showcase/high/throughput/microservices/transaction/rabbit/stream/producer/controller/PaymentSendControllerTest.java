package showcase.high.throughput.microservices.transaction.rabbit.stream.producer.controller;

import org.springframework.rabbit.stream.producer.RabbitStreamTemplate;
import showcase.high.throughput.microservices.domain.Payment;
import showcase.high.throughput.microservices.transaction.batch.mapping.TransactionToJsonBytesConverter;
import com.rabbitmq.stream.Message;
import com.rabbitmq.stream.MessageBuilder;
import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentSendControllerTest {

    @Mock
    private TransactionToJsonBytesConverter converter;

    @Mock
    private RabbitStreamTemplate producer;

    @Mock
    private MessageBuilder builder;
    @Mock
    private MessageBuilder.ApplicationPropertiesBuilder properties;

    @Mock
    private Message message;
    private PaymentSendController subject;
    private int workerCount = 3;
    private Payment transaction = JavaBeanGeneratorCreator.of(Payment.class).create();


    @BeforeEach
    void setUp() {
        subject = new PaymentSendController(producer, converter);
    }

    @Test
    void send() {

        int count = 10;
        subject.sendTransaction(transaction);

        verify(producer).convertAndSend(any());
    }
}