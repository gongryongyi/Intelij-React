package com.ohgiraffers.comprehensive.member.service;

import com.ohgiraffers.comprehensive.member.domain.Member;
import com.ohgiraffers.comprehensive.member.domain.repository.MemberRepository;
import com.ohgiraffers.comprehensive.member.dto.request.MemberSignupRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.reflect.MemberSignature;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
