package suftware.tuitui.common.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import suftware.tuitui.common.enumtype.TuiTuiMsgCode;
import suftware.tuitui.common.exception.TuiTuiException;
import suftware.tuitui.common.http.HttpResponseDto;

import java.util.HashMap;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    //  앱 관련 오류 예외처리 핸들러
    @ExceptionHandler(TuiTuiException.class)
    protected ResponseEntity<HttpResponseDto> handleTuiTuiException(TuiTuiException e){
        log.error("GlobalExceptionHandler.handleTuiTuiException() -> status: {}, code: {}, message: {}, data: {}",
                e.getMsg().getHttpStatus().toString(), e.getMsg().getCode(), e.getMsg().getMsg(), e.getObj());
        return ResponseEntity.status(e.getMsg().getHttpStatus()).body(HttpResponseDto.builder()
                .status(e.getMsg().getHttpStatus())
                .message(e.getMsg().getMsg())
                .code(e.getMsg().getCode())
                .data(e.getObj())
                .build());
    }

    //  서버 스크립트 오류 예외처리 핸들러
    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<HttpResponseDto> handleServerException(RuntimeException e){
        log.error("GlobalExceptionHandler.handleServerException() -> error Message: {}", e.getMessage());
        return ResponseEntity.status(TuiTuiMsgCode.INTERNAL_SERVER_ERROR.getHttpStatus()).body(HttpResponseDto.builder()
                .status(TuiTuiMsgCode.INTERNAL_SERVER_ERROR.getHttpStatus())
                .code(TuiTuiMsgCode.INTERNAL_SERVER_ERROR.getCode())
                .message(TuiTuiMsgCode.INTERNAL_SERVER_ERROR.getMsg())
                .build());
    }

    @MessageExceptionHandler(TuiTuiException.class)
    protected void handleMessageException(TuiTuiException e){
        log.error("GlobalExceptionHandler.handleMessageException() -> error code: {}, Message: {}", e.getMsg().getCode(), e.getMsg().getMsg());
    }
}
