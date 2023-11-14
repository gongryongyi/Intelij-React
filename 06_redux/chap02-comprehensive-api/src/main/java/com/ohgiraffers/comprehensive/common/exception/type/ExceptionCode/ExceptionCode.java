package com.ohgiraffers.comprehensive.common.exception.type.ExceptionCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ExceptionCode {

    FAIL_TO_UPLOAD_FILE(1001,"파일 저장에 실패하였습니다."),

    FAIL_TO_DELETE_FILE(1002, "파일 삭제에 실패하였습니다. "),

    NOT_FOUND_CATEGORY_CODE(2000, "카테고리 코드에 해당하는 카테고리가 존재하지 않습니다."),


    NOT_FOUND_PRODUCT_CODE(3000, "상품 코드에 해당하는 상품이 존재하지 않습니다. ");



    private final int code;
    private final String message;
}
