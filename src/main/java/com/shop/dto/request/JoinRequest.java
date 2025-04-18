package com.shop.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JoinRequest {

    @Email(message = "이메일 형식으로 입력해 주세요.")
    @NotBlank(message = "이메일을 입력해 주세요.")
    private String email;

    @NotBlank(message = "이름을 입력해 주세요.")
    private String name;

    @NotBlank(message = "비밀번호를 입력해 주세요.")
    private String password;
}
