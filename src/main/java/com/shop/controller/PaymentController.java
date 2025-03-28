package com.shop.controller;

import com.shop.dto.request.PaymentRequest;
import com.shop.dto.response.CommonResponse;
import com.shop.dto.response.OrderPaymentInfo;
import com.shop.dto.response.PaymentResponse;
import com.shop.service.PaymentService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/payment/widget/{orderId}")
    public String getPaymentWidget(@PathVariable Long orderId, Model model) {
        OrderPaymentInfo orderInfo = paymentService.getOrderPaymentInfo(orderId);
        model.addAttribute("orderInfo", orderInfo);
        return "widget/checkout";
    }

    @ResponseBody
    @PostMapping("/payment/confirm")
    public CommonResponse<PaymentResponse> confirmPayment(@RequestBody PaymentRequest request) {
        return paymentService.confirmPayment(request);
    }

    @GetMapping("/payment/success")
    public String success(@RequestParam String paymentKey,
                          @RequestParam String orderId,
                          @RequestParam Integer amount,
                          Model model) {
        model.addAttribute("paymentKey", paymentKey);
        model.addAttribute("orderId", orderId);
        model.addAttribute("amount", amount);
        return "widget/success";
    }

    @GetMapping("/payment/fail")
    public String failPayment(@RequestParam String code,
                              @RequestParam String message,
                              Model model) {
        model.addAttribute("code", code);
        model.addAttribute("message", message);
        return "fail";
    }

    @ResponseBody
    @GetMapping("/payments")
    public CommonResponse<List<PaymentResponse>> findAllPayments() {
        return paymentService.findAll();
    }
}