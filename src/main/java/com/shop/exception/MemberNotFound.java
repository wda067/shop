package com.shop.exception;

import static com.shop.exception.ErrorCode.MEMBER_NOT_FOUND;

public class MemberNotFound extends CustomException {

    public MemberNotFound() {
        super(MEMBER_NOT_FOUND);
    }
}
