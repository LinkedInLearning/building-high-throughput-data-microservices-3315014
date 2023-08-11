package showcase.high.throughput.microservices.transaction.batch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class JobCsvToApacheKafkaApp {

	public static void main(String[] args) {
		System.exit(SpringApplication.exit(
				SpringApplication.run(
						JobCsvToApacheKafkaApp.class, args)));
	}

}
