package com.ohgiraffers.comprehensive.jwt;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
public class CustomUser extends User {

    private final Long memberCode;

    public CustomUser(Long memberCode, UserDetails userDetails) {
        super(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
        this.memberCode = memberCode;
    }

    public static CustomUser of(Long memberCode, UserDetails userDetails) {
        return new CustomUser(
                memberCode,
                userDetails
        );
    }
}
