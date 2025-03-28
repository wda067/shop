package com.shop.exception;


import static com.shop.exception.ErrorCode.EMAIL_SEND_FAILURE;

public class EmailSendFailure extends CustomException {

    public EmailSendFailure() {
        super(EMAIL_SEND_FAILURE);
    }
}
