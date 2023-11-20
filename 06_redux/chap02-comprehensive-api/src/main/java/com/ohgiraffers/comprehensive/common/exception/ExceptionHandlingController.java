package com.ohgiraffers.comprehensive.common.exception;

import com.ohgiraffers.comprehensive.common.exception.type.ExceptionCode.ExceptionCode;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice // @Controller와의 차이 전역적인 익셉션 핸들링을
public class ExceptionHandlingController {

    /* 클라이언트 에러 --------------------------------------------------------*/
    /* 400 : Bad Request*/
      @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionResponse> badRequestException(BadRequestException e){

          final ExceptionResponse exceptionResponse
                  = ExceptionResponse.of(e.getCode(), e.getMessage());

          return ResponseEntity.badRequest().body(exceptionResponse);
          // badRequest = 400
      }
    /* 401 : UnAuthorized 인증 오류  => JwtAuthenticationEntryPoint 쪽에 이미 처리 되어 있음*/

    /* 403 : Forbidden 인가 오류 => handle 쪽에 이미 처리 되어 있음 */

    /* 404 : Not Found 클라이언트가 잘못하지 않았는데 찾을 수 없을때 */

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionResponse> notFoundException(NotFoundException e){

        final ExceptionResponse exceptionResponse
                = ExceptionResponse.of(e.getCode(), e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponse);

    }
    /* 409 : Conflict 충돌. 논리적으로 해당 기능을 수행할 수 없는 경우 처리 */
    //요청에 대한 형태는 잘 맞고 인증 잘됨 인가 잘됨 내용을 찾을수 없는 것도 아님 그치만 논리적으로 수행되지 않음
    // ex) 재고량이 알맞지 않아 요청할 수 없다

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ExceptionResponse> conflictException(ConflictException e){

        final ExceptionResponse exceptionResponse
                = ExceptionResponse.of(e.getCode(), e.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(exceptionResponse);

    }
    /* 서버 에러 --------------------------------------------------------------*/
    /* 500 */
    @ExceptionHandler(ServerInternalException.class)
    public ResponseEntity<ExceptionResponse> serverInternalException(ServerInternalException e){

        final ExceptionResponse exceptionResponse
                = ExceptionResponse.of(e.getCode(), e.getMessage());

        return ResponseEntity.internalServerError().body(exceptionResponse);  //이건 정말 서버 상 에서의 문제다

    }


    /* Validation Exception */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> methodValidException(MethodArgumentNotValidException e){

        String defaultMessage = e.getBindingResult().getFieldError().getDefaultMessage();


        final ExceptionResponse exceptionResponse = ExceptionResponse.of(9000, defaultMessage );

        return ResponseEntity.badRequest().body(exceptionResponse);
    }



}
