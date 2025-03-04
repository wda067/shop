package com.shop.domain.payment;

import com.shop.config.feign.TossPaymentsFeignConfig;
import com.shop.dto.request.PaymentRequest;
import com.shop.dto.response.PaymentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
        name = "tossPaymentsClient",
        url = "https://api.tosspayments.com/v1/payments",
        configuration = TossPaymentsFeignConfig.class
)
public interface TossPaymentsClient {

    @PostMapping(value = "/confirm")
    PaymentResponse confirmPayment(@RequestHeader("Authorization") String authorization,
                                   @RequestBody PaymentRequest request);
}
