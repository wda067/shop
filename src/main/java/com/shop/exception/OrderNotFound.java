package com.shop.exception;

import static com.shop.exception.ErrorCode.ORDER_NOT_FOUND;

public class OrderNotFound extends CustomException {

    public OrderNotFound() {
        super(ORDER_NOT_FOUND);
    }
}
