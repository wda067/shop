package com.shop.controller;

import com.shop.config.auth.Login;
import com.shop.dto.request.PaymentRequest;
import com.shop.dto.response.CommonResponse;
import com.shop.dto.response.OrderPaymentInfo;
import com.shop.dto.response.PaymentResponse;
import com.shop.service.EmailService;
import com.shop.service.PaymentService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/api/v1")
public class PaymentController {

    private final PaymentService paymentService;
    private final EmailService emailService;

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

    @PostMapping("/payment/email")
    public ResponseEntity<String> sendEmail(@Login Long memberId, @RequestBody PaymentRequest request) {
        emailService.send(memberId, request);
        return ResponseEntity.ok().body("email sent");
    }

    @ResponseBody
    @GetMapping("/payments")
    public CommonResponse<List<PaymentResponse>> findAllPayments() {
        return paymentService.findAll();
    }
}