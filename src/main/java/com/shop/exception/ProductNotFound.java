package com.shop.exception;

import static com.shop.enums.ErrorCode.PRODUCT_NOT_FOUND;

public class ProductNotFound extends CustomException {

    public ProductNotFound() {
        super(PRODUCT_NOT_FOUND);
    }
}
