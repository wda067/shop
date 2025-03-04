package com.shop.controller;

import com.shop.dto.request.JoinRequest;
import com.shop.dto.request.MemberSearch;
import com.shop.dto.response.CommonResponse;
import com.shop.dto.response.MemberResponse;
import com.shop.service.MemberService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/join")
    public void join(@RequestBody @Validated JoinRequest request) {
        memberService.join(request);
    }

    @PostMapping("/{memberId}/leave")
    public void leave(@PathVariable("memberId") Long memberId) {
        memberService.leave(memberId);
    }

    @GetMapping("/member/{memberId}")
    public CommonResponse<MemberResponse> get(@PathVariable("memberId") Long memberId) {
        return memberService.getMember(memberId);
    }

    @GetMapping("/members")
    public CommonResponse<List<MemberResponse>> getMembers(@ModelAttribute MemberSearch request) {
        return memberService.getMembers(request);
    }
}
