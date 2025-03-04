package com.shop.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    BAD_REQUEST("400", "잘못된 요청입니다."),
    EMAIL_ALREADY_EXISTS("409", "이미 가입된 이메일입니다."),
    PRODUCT_ALREADY_EXISTS("409", "이미 등록된 상품입니다."),
    EMAIL_SEND_FAILURE("500", "이메일 전송에 실패했습니다."),
    MEMBER_NOT_FOUND("404", "존재하지 않는 회원입니다."),
    ORDER_NOT_FOUND("404", "존재하지 않는 주문입니다."),
    ORDER_MEMBER_MISMATCH("403", "주문 번호와 회원 정보가 일치하지 않습니다."),
    LOGIN_FAILED("401", "이메일 또는 비밀번호가 잘못 되었습니다."),
    UNAUTHORIZED("401", "로그인 해주세요."),
    NOT_ENOUGH_STOCK("200", "재고가 부족합니다."),
    PRODUCT_NOT_FOUND("404", "존재하지 않는 상품입니다.");

    private final String code;
    private final String message;
}
