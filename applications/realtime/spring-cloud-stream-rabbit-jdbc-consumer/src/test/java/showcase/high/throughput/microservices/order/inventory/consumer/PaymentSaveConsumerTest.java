package showcase.high.throughput.microservices.order.inventory.consumer;

import showcase.high.throughput.microservices.domain.Payment;
import showcase.high.throughput.microservices.order.inventory.repository.PaymentJdbcRepository;
import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PaymentSaveConsumerTest {

      @Mock
      private PaymentJdbcRepository repository;

      @Mock
      private PaymentSaveConsumer subject;
      private Payment expected = JavaBeanGeneratorCreator.of(Payment.class).create();

      @Test
      void given_TransactionJdbcRepository_when_save_then_when_read_you_have_TransactionJdbcRepository_returned() {

          subject = new PaymentSaveConsumer(repository);

          subject.accept(expected);

          verify(this.repository).save(any(Payment.class));
      }
}