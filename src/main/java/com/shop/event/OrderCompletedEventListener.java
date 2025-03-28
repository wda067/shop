package com.shop.event;

import com.shop.exception.EmailSendFailure;
import com.shop.service.EmailService;
import com.shop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class OrderCompletedEventListener {

    private static final Logger orderLogger = LoggerFactory.getLogger("OrderLogger");

    private final EmailService emailService;
    private final OrderService orderService;

    @Async("emailTaskExecutor")
    @TransactionalEventListener
    public void handleOrderCompletedEvent(OrderCompletedEvent event) {
        try {
            emailService.sendOrderConfirmation(event.getEmail(), event.getOrderName());
        } catch (EmailSendFailure e) {
            orderLogger.error("code: {}, message: {}", e.getErrorCode().getCode(), e.getErrorCode().getMessage());
            orderService.cancel(event.getMemberId(), event.getOrderId());
        }
    }
}
