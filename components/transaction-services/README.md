
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

```roomsql
truncate ms_transactions ;
```


# Runs
## Postgres Chunk Size 10 - 8 threads


- First run about 13K TPS
- Second/Three about 16K TPS

```
(select max(job_execution_id) from batch_job_execution);
 job_execution_id |  total_time  |        tps         
------------------+--------------+--------------------
                9 | 00:02:01.508 | 16459.821575534121

```

## Postgres Chunk Size 10000 - 8 threads

```
 job_execution_id |  total_time  |        tps         
------------------+--------------+--------------------
               11 | 00:00:44.595 | 44848.077138692679
```


## Postgres Chunk Size 100000 - 8 threads

```text
 job_execution_id |  status   |  total_time  |   tps   
------------------+-----------+--------------+---------
               31 | COMPLETED | 00:00:16.064 | 124,502
```

## Postgres Chunk Size 300000 - 8 threads


```text
 job_execution_id |  status   |  total_time  |   tps   
------------------+-----------+--------------+---------
               39 | COMPLETED | 00:00:15.204 | 131,544
```


------------------

## RabbitMQ Streams


```text
(select max(job_execution_id) from batch_job_execution);
job_execution_id |  status   |  total_time  |   tps   
------------------+-----------+--------------+---------
6 | COMPLETED | 00:00:13.267 | 150,750
```
