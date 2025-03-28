package com.shop.exception;


import static com.shop.exception.ErrorCode.LOGIN_FAILED;

public class LoginFailed extends CustomException {

    public LoginFailed() {
        super(LOGIN_FAILED);
    }
}
