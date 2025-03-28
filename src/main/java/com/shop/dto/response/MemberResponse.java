package com.shop.dto.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.shop.domain.member.Member;
import lombok.Getter;

@Getter
public class MemberResponse {

    private final String email;
    private final String name;

    @JsonCreator
    public MemberResponse(Member member) {
        this.email = member.getEmail();
        this.name = member.getName();
    }
}