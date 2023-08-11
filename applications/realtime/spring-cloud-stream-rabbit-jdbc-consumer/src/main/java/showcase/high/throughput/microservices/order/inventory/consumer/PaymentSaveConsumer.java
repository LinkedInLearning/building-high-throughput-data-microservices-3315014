package showcase.high.throughput.microservices.order.inventory.consumer;

import showcase.high.throughput.microservices.domain.Payment;
import showcase.high.throughput.microservices.order.inventory.repository.PaymentJdbcRepository;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class PaymentSaveConsumer implements Consumer<Payment> {
    private final PaymentJdbcRepository repository;

    public PaymentSaveConsumer(PaymentJdbcRepository repository) {
        this.repository = repository;
    }

    @Override
    public void accept(Payment transaction) {
        repository.save(transaction);
    }
}
