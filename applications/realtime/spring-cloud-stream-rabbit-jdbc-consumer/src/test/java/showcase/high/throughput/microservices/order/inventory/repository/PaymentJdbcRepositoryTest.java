package showcase.high.throughput.microservices.order.inventory.repository;

import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import showcase.high.throughput.microservices.domain.Payment;

import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PaymentJdbcRepositoryTest {

    @Mock
    private PaymentJdbcRepository subject;
    @Mock
    private JdbcTemplate template;

    private final Payment order = JavaBeanGeneratorCreator.of(Payment.class).create();


    @BeforeEach
    void setUp() {
        subject = new PaymentJdbcRepository(template);
    }

    @Test
    void save() {
        subject.save(order);

        verify(template).update(anyString(), //sql
                anyString(), //id
                anyString(), // details
                anyString(), //contact
                anyString(), //location
                anyDouble(), //amount
                ArgumentMatchers.any()//amount
                );



    }


}