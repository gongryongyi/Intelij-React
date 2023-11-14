package com.ohgiraffers.comprehensive.product.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@RequiredArgsConstructor
/*
* @RequiredArgsConstructor: final이나 @NonNull 어노테이션이 붙은 필드에 대한 생성자를 생성합니다.
@AllArgsConstructor: 클래스의 모든 필드에 대한 생성자를 생성합니다.
* */
@Getter
public class ProductCreateRequest {

    @NotBlank
    private final String productName;
    @Min(value = 1)
    private final Long productPrice;
    @NotBlank
    private final String productDescription;
    @Min(value = 1)
    private final Long categoryCode;
    @Min(value = 1)
    private final Long productStock;
}
