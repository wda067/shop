package com.shop.event;

import com.shop.domain.order.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderEventPublisher {

    private final ApplicationEventPublisher eventPublisher;

    public void publishOrderCompleted(Order order) {
        eventPublisher.publishEvent(new OrderCompletedEvent(order.getMember(), order));
    }
}
