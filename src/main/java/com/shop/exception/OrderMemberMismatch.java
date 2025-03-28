package com.shop.exception;


import static com.shop.exception.ErrorCode.ORDER_MEMBER_MISMATCH;

public class OrderMemberMismatch extends CustomException {

    public OrderMemberMismatch() {
        super(ORDER_MEMBER_MISMATCH);
    }
}
