package showcase.high.throughput.microservices.transaction.batch.mapping;

import com.fasterxml.jackson.databind.ObjectMapper;
import showcase.high.throughput.microservices.domain.Payment;
import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author gregory green
 */
class PaymentToJsonBytesConverterTest {
    private TransactionToJsonBytesConverter subject;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        subject = new TransactionToJsonBytesConverter(objectMapper);
    }

    @Test
    void given_transaction_when_convert_then_convertJson() {

        Payment expected = JavaBeanGeneratorCreator.of(Payment.class).create();
        byte[] actual = subject.convert(expected);

        assertTrue(actual != null || actual.length > 0);
    }
}