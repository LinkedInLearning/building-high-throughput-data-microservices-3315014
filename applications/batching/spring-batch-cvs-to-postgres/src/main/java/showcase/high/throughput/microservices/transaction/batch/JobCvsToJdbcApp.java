package showcase.high.throughput.microservices.transaction.batch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JobCvsToJdbcApp {

	public static void main(String[] args) {
		System.exit(SpringApplication.exit(
				SpringApplication.run(
						JobCvsToJdbcApp.class, args)));
	}

}
