package com.shop.exception;

import static com.shop.exception.ErrorCode.FORBIDDEN;

public class Forbidden extends CustomException {

    public Forbidden() {
        super(FORBIDDEN);
    }
}
