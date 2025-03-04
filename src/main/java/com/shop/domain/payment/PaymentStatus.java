package com.shop.domain.payment;

public enum PaymentStatus {

    READY,
    IN_PROGRESS,
    WAITING_FOR_DEPOSIT,
    DONE,
    CANCELED,
    PARTIAL_CANCELED,
    ABORTED,
    EXPIRED;

    public static PaymentStatus fromString(String status) {
        return PaymentStatus.valueOf(status);
    }
}
