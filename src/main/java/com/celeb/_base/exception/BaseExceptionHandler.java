package com.celeb._base.exception;


import com.celeb._base.constant.Code;
import com.celeb._base.dto.ErrorResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;


@RestControllerAdvice(annotations = {RestController.class})
public class BaseExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
        WebRequest request) {
        String error = ex.getBindingResult().getFieldError().getDefaultMessage();
        return handleExceptionInternal(ex, Code.VALIDATION_ERROR, error);
//        return handleExceptionInternal(ex, Code.VALIDATION_ERROR, request);
    }

//    @ExceptionHandler
//    public ResponseEntity<Object> validation(ConstraintViolationException e, WebRequest request) {
//        return handleExceptionInternal(e, Code.VALIDATION_ERROR, request);
//    }

    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<Object> general(GeneralException e, WebRequest request) {
        return handleExceptionInternal(e, e.getErrorCode(), request);
    }

    @ExceptionHandler
    public ResponseEntity<Object> exception(Exception e, WebRequest request) {
        return handleExceptionInternal(e, Code.INTERNAL_ERROR, request);
    }


    protected ResponseEntity<Object> handleExceptionInternal(Exception e, Code errorCode,
        WebRequest request) {
        return ResponseEntity.internalServerError()
            .body(ErrorResponseDto.of(errorCode, errorCode.getMessage(e)));
    }

    protected ResponseEntity<Object> handleExceptionInternal(Exception e, Code errorCode,
        String message) {
        return ResponseEntity.internalServerError()
            .body(ErrorResponseDto.of(errorCode, message));
    }

//    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body,
//        HttpHeaders headers, HttpStatus status, WebRequest request) {
//        return handleExceptionInternal(ex, Code.valueOf(status), headers, status, request);
//    }
//
//
//    private ResponseEntity<Object> handleExceptionInternal(Exception e, Code errorCode,
//        WebRequest request) {
//        return handleExceptionInternal(e, errorCode, HttpHeaders.EMPTY, errorCode.getHttpStatus(),
//            request);
//    }

//    private ResponseEntity<Object> handleExceptionInternal(Exception e, Code errorCode,
//        HttpHeaders headers, HttpStatus status, WebRequest request) {
//        return super.handleExceptionInternal(
//            e,
//            ErrorResponseDto.of(errorCode, errorCode.getMessage(e)),
//            headers,
//            status,
//            request
//        );
//    }
}