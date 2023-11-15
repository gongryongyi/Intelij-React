package com.ohgiraffers.comprehensive.member.domain;

import com.ohgiraffers.comprehensive.member.domain.type.MemberRole;
import com.ohgiraffers.comprehensive.member.domain.type.MemberStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.*;

import java.time.LocalDateTime;

import static com.ohgiraffers.comprehensive.member.domain.type.MemberRole.USER;
import static com.ohgiraffers.comprehensive.member.domain.type.MemberStatus.ACTIVE;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "tbl_member")
@Getter
@NoArgsConstructor(access =  PROTECTED)
@EntityListeners(AuditingEntityListener.class) // 모디파이브된 날짜가 감지될때 자동으로 생성해준다.
public class Member {

    @Id
    @GeneratedValue(strategy = IDENTITY)  //오라클에서는 시퀀스를 썼지만 이거는 여기서는 자동으로 생성해준다
    private Long memberCode;

    @Column(nullable = false)
    private String memberId;

    @Column(nullable = false)
    private String memberPassword;

    @Column(nullable = false)
    private String memberName;

    private String memberEmail;

    @CreatedDate//생성시간 /해당행이 생성된 순간에 대해서 자동으로 처리를 해준다. 그럼 굳이 이컬럼을 다루지 않아도 된다.
    @Column(nullable = false)
    private LocalDateTime createdAt;



    @LastModifiedDate//수정시간 / 해당행이 생성된 순간에 대해서 자동으로 처리를 해준다. 그럼 굳이 이컬럼을 다루지 않아도 된다.
    @Column(nullable = false)
    private LocalDateTime modifiedAt;


    @Column(nullable = false)
    @Enumerated(value = STRING)
    private MemberStatus status = ACTIVE;

    @Column(nullable = false)
    @Enumerated(value = STRING)     // 숫자대신 문자열로 받기
    private MemberRole memberRole = USER;


    private String refreshToken;

    public Member(String memberId, String memberPassword, String memberName, String memberEmail) {
        this.memberId = memberId;
        this.memberPassword = memberPassword;
        this.memberName = memberName;
        this.memberEmail = memberEmail;

    }

    public static Member of(String memberId, String memberPassword, String memberName, String memberEmail) {

        return new Member(
                memberId,
                memberPassword,
                memberName,
                memberEmail
        );

    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;

    }
}
