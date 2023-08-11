package showcase.high.throughput.microservices.transaction.batch.listener;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;

/**
 * @author gregory green
 */
@Component
public class LogJobExecutionListener extends JobExecutionListenerSupport {
    @Override
    public void afterJob(JobExecution jobExecution) {
        System.out.println("jobExecution:"+jobExecution);
    }
}
