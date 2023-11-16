package com.ohgiraffers.comprehensive.member.service;

import com.ohgiraffers.comprehensive.common.exception.BadRequestException;
import com.ohgiraffers.comprehensive.common.exception.type.ExceptionCode.ExceptionCode;
import com.ohgiraffers.comprehensive.member.domain.Member;
import com.ohgiraffers.comprehensive.member.domain.repository.MemberRepository;
import com.ohgiraffers.comprehensive.member.dto.request.MemberSignupRequest;
import com.ohgiraffers.comprehensive.member.dto.response.ProfileResponse;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.reflect.MemberSignature;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.ohgiraffers.comprehensive.common.exception.type.ExceptionCode.ExceptionCode.NOT_FOUND_MEMBER_ID;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    /* 1. 회원가입 */


    public void signup(final MemberSignupRequest memberRequest){

        final Member newMember = Member.of(
               memberRequest.getMemberId(),
                passwordEncoder.encode(memberRequest.getMemberPassword()),
                memberRequest.getMemberName(),
                memberRequest.getMemberEmail()

        );

        memberRepository.save(newMember);  //save를 통해 여기다가 엔티티를 저장한다.
    }

    @Transactional(readOnly = true)
    public ProfileResponse getprofile(String memberId) {

        final Member member = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_MEMBER_ID));


        return ProfileResponse.from(member);

    }
}
