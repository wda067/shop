package com.shop.exception;

import static com.shop.exception.ErrorCode.UNAUTHORIZED;

public class Unauthorized extends CustomException {

    public Unauthorized() {
        super(UNAUTHORIZED);
    }
}
