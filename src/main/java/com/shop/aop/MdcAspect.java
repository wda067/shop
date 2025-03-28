package com.shop.aop;

import com.shop.dto.request.PaymentRequest;
import java.util.Arrays;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MdcAspect {

    @Before("execution(* com.shop.controller.PaymentController.confirmPayment(..))")
    public void setMdcForOrder(JoinPoint joinPoint) {
        Arrays.stream(joinPoint.getArgs())
                .filter(arg -> arg instanceof PaymentRequest)
                .findFirst()
                .ifPresent(arg -> {
                    String orderId = ((PaymentRequest) arg).getOrderId();
                    if (orderId != null) {
                        MDC.put("orderId", orderId);
                    }
                });
    }

    @AfterReturning("execution(* com.shop.controller.PaymentController.confirmPayment(..))")
    public void clearMdc() {
        MDC.clear();
    }

    @AfterThrowing("execution(* com.shop.controller.PaymentController.confirmPayment(..))")
    public void clearMdcAfterThrowing() {
        MDC.clear();
    }
}
