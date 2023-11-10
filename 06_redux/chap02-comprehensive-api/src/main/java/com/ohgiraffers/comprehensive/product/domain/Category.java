package com.ohgiraffers.comprehensive.product.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "tbl_category")
@NoArgsConstructor(access = PROTECTED)
@Getter
public class Category {

    @Id
    private Long categoryCode;
    @Column(nullable = false)  //없어도 상관없음 단 속성 붙여줄때는 필요함 예)(nullable = false)
    private String categoryName;  //네이밍만 일치 시켜주면 됨

}
