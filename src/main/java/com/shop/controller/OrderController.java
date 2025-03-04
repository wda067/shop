package com.shop.controller;

import com.shop.config.auth.Login;
import com.shop.dto.request.OrderRequest;
import com.shop.dto.response.CommonResponse;
import com.shop.dto.response.OrderResponse;
import com.shop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/order")
    public void order(@Login Long memberId, @RequestBody @Validated OrderRequest request) {
        orderService.order(memberId, request);
    }

    @PostMapping("/order/{orderId}/cancel")
    public void cancel(@Login Long memberId, @PathVariable Long orderId) {
        orderService.cancel(memberId, orderId);
    }

    @GetMapping("/order/{orderId}")
    public CommonResponse<OrderResponse> getMyOrder(@Login Long memberId, @PathVariable Long orderId) {
        return orderService.get(memberId, orderId);
    }
}
