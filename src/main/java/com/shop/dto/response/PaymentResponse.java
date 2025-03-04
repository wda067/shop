package com.shop.dto.response;

import static java.time.LocalDateTime.parse;
import static java.time.format.DateTimeFormatter.ofPattern;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.shop.domain.payment.Payment;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)  //null인 필드는 JSON에서 제외
@JsonIgnoreProperties(ignoreUnknown = true)  //정의되지 않은 필드는 역직렬화할 때 무시
public class PaymentResponse {

    private final String paymentKey;
    private final String orderId;
    private final String orderName;
    private final String method;
    private final Long totalAmount;
    private final String status;
    private final LocalDateTime requestedAt;

    @JsonCreator
    public PaymentResponse(
            @JsonProperty("paymentKey") String paymentKey,
            @JsonProperty("orderId") String orderId,
            @JsonProperty("orderName") String orderName,
            @JsonProperty("method") String method,
            @JsonProperty("totalAmount") Long totalAmount,
            @JsonProperty("status") String status,
            @JsonProperty("requestedAt") String requestedAt) {
        this.paymentKey = paymentKey;
        this.orderId = orderId;
        this.orderName = orderName;
        this.method = method;
        this.totalAmount = totalAmount;
        this.status = status;
        this.requestedAt = parse(requestedAt, ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX"));
    }

    public PaymentResponse(Payment payment) {
        this.paymentKey = payment.getPaymentKey();
        this.orderId = String.valueOf(payment.getOrder().getId());
        this.orderName = payment.getOrderName();
        this.method = payment.getMethod();
        this.totalAmount = payment.getTotalAmount();
        this.status = payment.getStatus().name();
        this.requestedAt = payment.getRequestedAt();
    }

}
