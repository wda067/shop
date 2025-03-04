package com.shop.exception;

import static com.shop.enums.ErrorCode.UNAUTHORIZED;

public class Unauthorized extends CustomException {

    public Unauthorized() {
        super(UNAUTHORIZED);
    }
}
