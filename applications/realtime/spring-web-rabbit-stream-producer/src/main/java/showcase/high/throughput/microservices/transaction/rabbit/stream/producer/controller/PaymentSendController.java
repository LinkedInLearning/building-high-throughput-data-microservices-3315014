package showcase.high.throughput.microservices.transaction.rabbit.stream.producer.controller;

import org.springframework.rabbit.stream.producer.RabbitStreamTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import showcase.high.throughput.microservices.domain.Payment;
import showcase.high.throughput.microservices.transaction.batch.mapping.TransactionToJsonBytesConverter;

@RestController
@RequestMapping("payments")
public class PaymentSendController {

    private final RabbitStreamTemplate producer;
    private final TransactionToJsonBytesConverter converter;
    private String contentType = "contentType";
    private String jsonContentType = "application/json";

    public PaymentSendController(RabbitStreamTemplate producer, TransactionToJsonBytesConverter converter) {
        this.producer = producer;
        this.converter = converter;
    }

    @PostMapping
    @RequestMapping("transaction")
    void sendTransaction(@RequestBody Payment transaction)
    {
        producer.convertAndSend(transaction);
    }

}
