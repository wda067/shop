package com.shop.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentRequest {

    @NotBlank
    private final String paymentKey;

    @NotBlank
    private final String orderId;

    @NotBlank
    private final String amount;
}
