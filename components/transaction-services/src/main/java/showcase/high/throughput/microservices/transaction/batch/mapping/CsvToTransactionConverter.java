package showcase.high.throughput.microservices.transaction.batch.mapping;

import showcase.high.throughput.microservices.domain.Payment;
import nyla.solutions.core.patterns.conversion.Converter;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author gregory green
 */
@Component
public class CsvToTransactionConverter implements Converter<List<String>, Payment> {
    @Override
    public Payment convert(List<String> csvRow) {
        return new Payment(
                csvRow.get(0), //id
                csvRow.get(1), //detials
                csvRow.get(2), //contact
                csvRow.get(3), //location
                Double.valueOf(csvRow.get(4)), //amount
                Timestamp.valueOf(csvRow.get(5))
        );
    }
}
