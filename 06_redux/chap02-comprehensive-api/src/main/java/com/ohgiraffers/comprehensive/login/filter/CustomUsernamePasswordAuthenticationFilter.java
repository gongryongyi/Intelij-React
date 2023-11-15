package com.ohgiraffers.comprehensive.login.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.parameters.P;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StreamUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;


/* 스프링 시큐리티의 기존 UsernamePasswordAuthenticationFilter를 대체할 Custom Filter 작성 */
public class CustomUsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private static final String HTTP_METHOD = "POST";
    private static final String LOGIN_REQUEST_URL = "/member/login";  //이러한 동작이 있을때 아래의 메소드로 감
    private static final String CONTENT_TYPE = "application/json";

    private static final String USERNAME = "memberId";
    private static final  String PASSWORD = "memberPassword";

    private final ObjectMapper objectMapper;


    public CustomUsernamePasswordAuthenticationFilter(ObjectMapper objectMapper){// 어떠한 요청이 왔을때 처리할 메소드
        /*POST "/member/login" 요청이 왔을때 해당 필터가 동작하도록 설정 */
        super(new AntPathRequestMatcher(LOGIN_REQUEST_URL, HTTP_METHOD));
        this.objectMapper = objectMapper;
    }
    /* POST "/member/login" 요청 발생 시 메소드 호출되며 인증 처리 작성 */
    @Override  // 필터 거칠때 호출되는 메소드
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException {
        //Request Content Type "application/json" 확인
        if (request.getContentType() == null || !request.getContentType().equals(CONTENT_TYPE)){
            throw new AuthenticationServiceException("Content-Type not supported");
        }

        // Request Body 읽어오기
        String body = StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);
        //JSON 문자열을 Java Map 타입으로 변환
        Map<String, String> bodyMap = objectMapper.readValue(body, Map.class);  //body문자열을 readValue 읽어와서 Map타입으로 변경하겠다.

        //key 값을 전달해서 Map에서 id와 pwd꺼내기
        String memberId = bodyMap.get(USERNAME);
        String memberPassword = bodyMap.get(PASSWORD);

        // 인증 토큰에 세팅
        UsernamePasswordAuthenticationToken authenticationToken
                =new UsernamePasswordAuthenticationToken(memberId, memberPassword);


        // 인증 매니저에게 인증 토큰 전달
        return this.getAuthenticationManager().authenticate(authenticationToken);


    }


}
