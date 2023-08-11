

```properties
batch.chunk.size=10000
batch.core.pool.size=16
batch.file.location=/Users/Projects/solutions/onLine-Learning/Linked-In-Learning/High-Throughput/microservice-high-throughput/scripts/generate_batch_file/runtime/transactions.csv
spring.kafka.producer.value-serializer=org.springframework.kafka.support.serializer.JsonSerializer
spring.kafka.producer.acks=1
spring.application.name=spring-batch-csv-to-rabbit-stream
```

```shell
java -Xms5g -Xmx5g -jar applications/batching/spring-batch-csv-to-apache-kafka/target/spring-batch-csv-to-apache-kafka-0.0.1-SNAPSHOT.jar
```


Report on 

job_execution_id |  status   | total_time  |   tps


```roomsql
select job.job_execution_id, job.status, job.end_time - job.start_time as total_time,
     to_char(
     ( select step.write_count from batch_step_execution step where step.job_execution_id= job.job_execution_id)
     /(extract(epoch from end_time)  - extract(epoch from start_time)),'FM9,999,999') as tps
from batch_job_execution job
where job_execution_id =
(select max(job_execution_id) from batch_job_execution);
```


Clean up table

------------------
Spring Batch version 2.7
Java version 17

---------

# Apache Kafka (1 ACKS)


- CSV lines=2,000,000
- batch.chunk.size=700000
- thread count=1
- spring.kafka.producer.acks=1
- Completion time 28.8 seconds

Kafka  version kafka_2.13-3.4.0


| total_time   | tps      |
|--------------|----------|
| 00:00:28.085 |  71,212   |


job_execution_id |  status   |  total_time  |  tps   
------------------+-----------+--------------+--------
40 | COMPLETED | 00:00:27.005 | 74,060

