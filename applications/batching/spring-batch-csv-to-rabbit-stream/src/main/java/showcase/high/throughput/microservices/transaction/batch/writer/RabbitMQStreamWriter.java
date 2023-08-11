package showcase.high.throughput.microservices.transaction.batch.writer;

import showcase.high.throughput.microservices.domain.Payment;
import com.rabbitmq.stream.ConfirmationHandler;
import com.rabbitmq.stream.Producer;
import nyla.solutions.core.patterns.conversion.Converter;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author gregory green
 */
@Component
public class RabbitMQStreamWriter implements ItemWriter<Payment> {

    private final Producer producer;
    private final Converter<Payment,byte[]> serializer;
    private final ConfirmationHandler handler;
    private static final AtomicLong count = new AtomicLong();

    public RabbitMQStreamWriter(Producer producer, Converter<Payment,byte[]> serializer) {
        this.producer = producer;
        this.serializer = serializer;
        //Publish Confirm with an atomic long
        this.handler = confirmationStatus -> {count.addAndGet(1);};
    }

    @Override
    public void write(List<? extends Payment> items) throws Exception {
        items.forEach(transaction ->
                        producer.send(producer.messageBuilder()
                                        .applicationProperties().entry("contentType",
                                        "application/json").messageBuilder()
                                .addData(serializer.convert(transaction)).build(),handler));
    }
}

