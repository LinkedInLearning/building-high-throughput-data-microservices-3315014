package showcase.high.throughput.microservices.transaction.batch.mapping;

import showcase.high.throughput.microservices.domain.Payment;
import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.List;

import static java.lang.String.valueOf;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author gregory green
 */
class CsvToPaymentConverterTest {

    private CsvToTransactionConverter subject;

    @Test
    void given_csv_when_convert_then_return_transaction() {

        var expected = JavaBeanGeneratorCreator.of(Payment.class).create();
        List<String> csv = asList(
                expected.id(),
                expected.details(),
                expected.contact(),
                expected.location(),
                valueOf(expected.amount()),
                valueOf(expected.timestamp())
        );

        subject = new CsvToTransactionConverter();

        var actual = subject.convert(csv);
        assertEquals(expected, actual);
    }

    @Test
    void timeParsing() {

        String timeText = "2022-12-23 08:25:28.287032";
        Timestamp.valueOf(timeText);


    }
}