package com.shop.exception;


import static com.shop.enums.ErrorCode.EMAIL_ALREADY_EXISTS;

public class EmailAlreadyExists extends CustomException {

    public EmailAlreadyExists() {
        super(EMAIL_ALREADY_EXISTS);
    }
}
