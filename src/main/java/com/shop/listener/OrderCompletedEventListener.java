package com.shop.listener;

import com.shop.event.OrderCompletedEvent;
import com.shop.exception.EmailSendFailure;
import com.shop.service.EmailService;
import com.shop.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderCompletedEventListener {

    private final EmailService emailService;
    private final OrderService orderService;

    @Async("asyncExecutor")
    @TransactionalEventListener
    public void handleOrderCompletedEvent(OrderCompletedEvent event) {
        try {
            emailService.sendOrderConfirmation(event.getEmail(), event.getOrderName());
        } catch (EmailSendFailure e) {
            log.error("code: {}, message: {}", e.getErrorCode().getCode(), e.getErrorCode().getMessage());
            orderService.cancel(event.getMemberId(), event.getOrderId());
        }
    }
}
