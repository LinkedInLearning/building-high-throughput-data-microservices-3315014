package showcase.high.throughput.microservices.transaction.batch.mapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import showcase.high.throughput.microservices.domain.Payment;
import nyla.solutions.core.patterns.conversion.Converter;
import org.springframework.stereotype.Component;

/**
 * @author gregory green
 */
@Component
public class TransactionToJsonBytesConverter implements Converter<Payment,byte[]> {
    private final ObjectMapper objectMapper;

    public TransactionToJsonBytesConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public byte[] convert(Payment transaction) {
        try {
            return objectMapper.writeValueAsBytes(transaction);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
