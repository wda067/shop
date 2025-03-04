package com.shop.event;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.shop.domain.Member;
import com.shop.domain.order.Order;
import lombok.Getter;

@Getter
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class OrderCompletedEvent {

    private final Long memberId;
    private final Long orderId;
    private final String email;
    private final String orderName;

    @JsonCreator
    public OrderCompletedEvent(
            @JsonProperty("memberId") Long memberId,
            @JsonProperty("orderId") Long orderId,
            @JsonProperty("email") String email,
            @JsonProperty("orderName") String orderName
    ) {
        this.memberId = memberId;
        this.orderId = orderId;
        this.email = email;
        this.orderName = orderName;
    }

    public OrderCompletedEvent(Member member, Order order) {
        this.memberId = member.getId();
        this.orderId = order.getId();
        this.email = member.getEmail();
        this.orderName = order.getOrderName();
    }
}
