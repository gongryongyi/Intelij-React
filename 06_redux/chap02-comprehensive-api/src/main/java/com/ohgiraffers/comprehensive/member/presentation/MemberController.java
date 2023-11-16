package com.ohgiraffers.comprehensive.member.presentation;

import com.ohgiraffers.comprehensive.member.dto.request.MemberSignupRequest;
import com.ohgiraffers.comprehensive.member.dto.response.ProfileResponse;
import com.ohgiraffers.comprehensive.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping({"/member", "/api/v1/member"})
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

    /* 2. 프로필 조회 */
    ///api/v1/member -> GetMapping  인증되면 처리한다
    @GetMapping
    public ResponseEntity<ProfileResponse> profile(@AuthenticationPrincipal User user){

        ProfileResponse profileResponse = memberService.getprofile(user.getUsername());

        return ResponseEntity.ok(profileResponse);
    }

}
