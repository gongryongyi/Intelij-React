package com.ohgiraffers.comprehensive.member.controller;

import com.ohgiraffers.comprehensive.member.dto.request.MemberSignupRequest;
import com.ohgiraffers.comprehensive.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor // 생성자 자동으로 만듬
public class MemberController {


    private final MemberService memberService;

    /* 1. 회원가입 */

    //ResponseEntity<Void>은 HTTP 응답을 나타내며, 응답 바디가 없음을 나타냅니다.
    // 이것은 주로 어떤 작업이 성공적으로 수행되었음을 나타내는 경우에 사용됩니다.
    @PostMapping("/signup")  //HTTP 메소드 Post
    public ResponseEntity<Void> signup(@RequestBody @Valid MemberSignupRequest memberRequest){// 데이터 검증 Valid
        memberService.signup(memberRequest);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
