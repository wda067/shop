package com.shop.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.domain.member.Member;
import com.shop.dto.request.JoinRequest;
import com.shop.repository.member.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberRepository memberRepository;

    @AfterEach
    void tearDown() {
        memberRepository.deleteAll();
    }

    @Test
    void 정상적인_요청을_하면_회원가입이_성공한다() throws Exception {
        //given
        JoinRequest request = new JoinRequest(
                "test@test.com",
                "test",
                "password");
        String json = objectMapper.writeValueAsString(request);

        //when
        mockMvc.perform(post("/api/join")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(print());

        //then
        assertEquals(1, memberRepository.count());
        assertTrue(memberRepository.findByEmail("test@test.com")
                .isPresent());
    }

    @Test
    void 중복된_이메일로_회원가입할_수_없다() throws Exception {
        //given
        Member member = Member.builder()
                .email("test@test.com")
                .name("test")
                .password("password")
                .build();
        memberRepository.save(member);

        JoinRequest request = new JoinRequest(
                "test@test.com",
                "test2",
                "password");
        String json = objectMapper.writeValueAsString(request);

        //when
        mockMvc.perform(post("/api/join")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isConflict())
                .andDo(print());

        //then
        assertEquals(1, memberRepository.count());
    }

    @Test
    void 이메일_형식이_잘못되면_회원가입이_실패한다() throws Exception {
        //given
        JoinRequest request = new JoinRequest(
                "invalidEmail",
                "test",
                "password");
        String json = objectMapper.writeValueAsString(request);

        //when
        ResultActions resultActions = mockMvc.perform(post("/api/join")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andDo(print());

        //then
        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.validation.email").value("이메일 형식으로 입력해 주세요."));
    }

    @Test
    void 이름을_입력하지_않으면_회원가입이_실패한다() throws Exception {
        //given
        JoinRequest request = new JoinRequest(
                "test@test.com",
                "",
                "password");
        String json = objectMapper.writeValueAsString(request);

        //when
        ResultActions resultActions = mockMvc.perform(post("/api/join")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andDo(print());

        //then
        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.validation.name").value("이름을 입력해 주세요."));
    }

    @Test
    void 비밀번호를_입력하지_않으면_회원가입이_실패한다() throws Exception {
        //given
        JoinRequest request = new JoinRequest(
                "test@test.com",
                "test",
                "");
        String json = objectMapper.writeValueAsString(request);

        //expected
        ResultActions resultActions = mockMvc.perform(post("/api/join")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andDo(print());

        //then
        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.validation.password").value("비밀번호를 입력해 주세요."));
    }
}