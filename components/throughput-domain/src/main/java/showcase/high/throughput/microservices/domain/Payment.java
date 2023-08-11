package showcase.high.throughput.microservices.domain;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author gregory green
 */
public record Payment(String id, String details, String contact, String location, double amount, Timestamp timestamp)
        implements Serializable {
}
