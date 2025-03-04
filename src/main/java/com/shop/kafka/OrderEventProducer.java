package com.shop.kafka;

import com.shop.event.OrderCompletedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class OrderEventProducer {

    private static final String TOPIC = "order-completed";

    private final KafkaTemplate<String, OrderCompletedEvent> kafkaTemplate;

    public void sendOrderCompletedEvent(OrderCompletedEvent event) {
        kafkaTemplate.send(TOPIC, event)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        log.info("Kafka Message Sent: key={}, partition={}, offset={}",
                                result.getProducerRecord().key(),
                                result.getRecordMetadata().partition(),
                                result.getRecordMetadata().offset());
                    } else {
                        log.error("Kafka Message Send Failed: {}", ex.getMessage(), ex);
                    }
                });
    }
}
