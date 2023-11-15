package com.ohgiraffers.comprehensive.jwt.service;


import com.ohgiraffers.comprehensive.common.exception.BadRequestException;
import com.ohgiraffers.comprehensive.member.domain.Member;
import com.ohgiraffers.comprehensive.member.domain.repository.MemberRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

import static com.ohgiraffers.comprehensive.common.exception.type.ExceptionCode.ExceptionCode.NOT_FOUND_MEMBER_ID;

@Service
@Slf4j
public class JwtService {



    @Value("${jwt.access.expiration}")
    private Long accessTokenExpirationPeriod;
    @Value("${jwt.refresh.expiration}")
    private Long refreshTokenExpirationPeriod;


    private final Key key;  // 서명 키
    private final MemberRepository memberRepository;

    private static final String ACCESS_TOKEN_SUBJECT = "AccessToken";
    private static final String REFRESH_TOKEN_SUBJECT = "RefreshToken";

    private static final String BEARER = "Bearer "; //Bearer 뒤에 토큰쓰기때문에 꼭 띄어쓰기 해줘야함

    public JwtService(@Value("${jwt.secret}") String scretKey, MemberRepository memberRepository){
        byte[] keyBytes = Decoders.BASE64.decode(scretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.memberRepository = memberRepository;
    }

    public String createAccessToken(Map<String, String> memberInfo) {

        Claims claims = Jwts.claims().setSubject(ACCESS_TOKEN_SUBJECT);
        claims.putAll(memberInfo);

        return Jwts.builder()
                .setClaims(claims) //몸체?
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpirationPeriod))  //만료시간
                .signWith(key, SignatureAlgorithm.HS512)  //시큐리티 키를 넣었다
                .compact();
    }

    public String createRefreshToken() {
        return Jwts.builder()
                .setSubject(REFRESH_TOKEN_SUBJECT)
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpirationPeriod))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    @Transactional
    public void updateRefreshToken(String memberId, String refreshToken) {
        memberRepository.findByMemberId(memberId)
                .ifPresentOrElse(  //ifPresent만약 있으면 member -> member.updateRefreshToken(refreshToken) 이렇게
                        // 없으면 OrElse () -> new BadRequestException(NOT_FOUND_MEMBER_ID) 이렇게 익셉션 발생시키겠다.
                        member -> member.updateRefreshToken(refreshToken),
                        () -> new BadRequestException(NOT_FOUND_MEMBER_ID)
                );




    }

    public Optional<String> getRefreshToken(HttpServletRequest request) {  //넘어왔을수도 있고 안넘어 왔을수도 있기때문에 (즉 null 일수도 있다. )Optional 사용
        return Optional.ofNullable(request.getHeader("Refresh-Token"))
                .filter(refreshToken -> refreshToken.startsWith(BEARER))  // 있으면 filter 탈꺼고 bearer이 true면
                .map(refreshToken -> refreshToken.replace(BEARER, ""));  //여기를 탈꺼다 그리고 없으면 null 반환
    }

    public boolean isValidToken(String token) { //token = 추출된 문자열

        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        }catch (Exception e){
            log.error("유효하지 않은 토큰입니다. {}", e.getMessage());
           return false;
        }
        
        
        
    }

    public void checkRefreshTokenAndReIssueAccessToken(HttpServletResponse response, String refreshToken) {
        memberRepository.findByRefreshToken(refreshToken)
                .ifPresent(member -> {
                    String reIssuedRefreshToken = reIssuedRefreshToken(member);

                    String accessToken = createAccessToken(
                            Map.of("memberId", member.getMemberId(), "memberRole", member.getMemberRole().name())
                    );
                    response.setHeader("Access-Token", accessToken);
                    response.setHeader("Refresh-Token", reIssuedRefreshToken);

                });
    }

    private String reIssuedRefreshToken(Member member) {
        String reIssuedRefreshToken = createRefreshToken();  //refresh 토큰을 발급한다.
        member.updateRefreshToken(reIssuedRefreshToken);  //디비에서 새롭게 발급된 refresh 토큰으로 바뀌어야한다.
        memberRepository.saveAndFlush(member);  //saveAndFlush = 바로 이순간 DB쪽으로 내보낸다
        return reIssuedRefreshToken;
    }
}