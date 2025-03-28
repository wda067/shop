package com.shop.exception;


import static com.shop.exception.ErrorCode.PRODUCT_ALREADY_EXISTS;

public class ProductAlreadyExists extends CustomException {

    public ProductAlreadyExists() {
        super(PRODUCT_ALREADY_EXISTS);
    }
}
