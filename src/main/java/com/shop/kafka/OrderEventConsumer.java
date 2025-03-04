package com.shop.kafka;

import com.shop.exception.EmailSendFailure;
import com.shop.service.EmailService;
import com.shop.service.OrderService;
import com.shop.event.OrderCompletedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEventConsumer {

    private final EmailService emailService;
    private final OrderService orderService;

    @KafkaListener(topics = "order-completed", groupId = "order-group", containerFactory = "kafkaListenerContainerFactory")
    public void listenOrderCompleted(OrderCompletedEvent event) {
        log.info("Received order completed event: {}", event);
        try {
            emailService.sendOrderConfirmation(event.getEmail(), event.getOrderName());
        } catch (EmailSendFailure e) {
            log.error("code: {}, message: {}", e.getErrorCode().getCode(), e.getErrorCode().getMessage());
            orderService.cancel(event.getMemberId(), event.getOrderId());
        }
    }
}
