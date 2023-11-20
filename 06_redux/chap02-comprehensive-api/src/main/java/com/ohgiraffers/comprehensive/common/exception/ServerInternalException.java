package com.ohgiraffers.comprehensive.common.exception;

import com.ohgiraffers.comprehensive.common.exception.type.ExceptionCode.ExceptionCode;

public class ServerInternalException extends CustomException{


    public ServerInternalException(final ExceptionCode exceptionCode){
        super(exceptionCode);
    }
}
