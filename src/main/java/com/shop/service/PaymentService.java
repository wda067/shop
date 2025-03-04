package com.shop.service;

import static java.nio.charset.StandardCharsets.UTF_8;

import com.shop.domain.Member;
import com.shop.domain.order.Order;
import com.shop.domain.payment.Payment;
import com.shop.domain.payment.TossPaymentsClient;
import com.shop.dto.request.PaymentRequest;
import com.shop.dto.response.CommonResponse;
import com.shop.dto.response.OrderPaymentInfo;
import com.shop.dto.response.PaymentResponse;
import com.shop.exception.OrderNotFound;
import com.shop.repository.OrderRepository;
import com.shop.repository.PaymentRepository;
import java.util.Base64;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final TossPaymentsClient tossPaymentsClient;

    @Value("${toss-payments.widget-secret}")
    private String widgetSecretKey;

    public OrderPaymentInfo getOrderPaymentInfo(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(OrderNotFound::new);
        Member member = order.getMember();
        order.processPayment();

        return new OrderPaymentInfo(order, member);
    }

    @Transactional
    public CommonResponse<PaymentResponse> confirmPayment(PaymentRequest request) {
        PaymentResponse response = tossPaymentsClient.confirmPayment(getAuthHeader(), request);
        Order order = orderRepository.findById(decodeOrderId(response))
                .orElseThrow(OrderNotFound::new);

        Payment payment = new Payment(response, order);
        paymentRepository.save(payment);

        return CommonResponse.success(response);
    }

    private Long decodeOrderId(PaymentResponse response) {
        String cleaned = response.getOrderId().replaceAll("-", "");
        byte[] decode = Base64.getDecoder().decode(cleaned);
        String rawOrderId = new String(decode);
        return Long.parseLong(rawOrderId);
    }

    private String getAuthHeader() {
        byte[] bytes = (widgetSecretKey + ":").getBytes(UTF_8);
        return "Basic " + Base64.getEncoder().encodeToString(bytes);
    }

    @Transactional
    public CommonResponse<List<PaymentResponse>> findAll() {
        return CommonResponse.success(paymentRepository.findAll().stream()
                .map(PaymentResponse::new)
                .toList());
    }
}
