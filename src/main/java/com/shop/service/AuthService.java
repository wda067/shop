package com.shop.service;

import com.shop.domain.member.Member;
import com.shop.dto.request.LoginRequest;
import com.shop.exception.LoginFailed;
import com.shop.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Transactional(readOnly = true)
    public Member login(LoginRequest request) {
        return memberRepository.findByEmail(request.getEmail())
                .filter(member -> passwordEncoder.matches(request.getPassword(), member.getPassword()))
                .orElseThrow(LoginFailed::new);
    }
}
