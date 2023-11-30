package com.celeb._base.constant;

import com.celeb._base.exception.GeneralException;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum Code {
    OK(200, HttpStatus.OK, "OK"),
    BAD_REQUEST(2000, HttpStatus.BAD_REQUEST, "Bad request"),
    VALIDATION_ERROR(2001, HttpStatus.BAD_REQUEST, "Validation error"),

    // User

    NOT_FOUND_USER(3001, HttpStatus.NOT_FOUND, "User not found"),
    EMPTY_EMAIL(3002, HttpStatus.BAD_REQUEST, "Email is empty"),
    EMPTY_NAME(3003, HttpStatus.BAD_REQUEST, "Name is empty"),
    ALREADY_EXISTS_USER(3004, HttpStatus.BAD_REQUEST, "Already exists user"),
    EMPTY_PASSWORD(3005, HttpStatus.BAD_REQUEST, "Password is empty"),
    EMPTY_GENDER(3006, HttpStatus.BAD_REQUEST, "Gender is empty"),
    EMPTY_USER(3007, HttpStatus.BAD_REQUEST, "User is empty"),
    INVALID_PASSWORD(3008,HttpStatus.BAD_REQUEST, "Password is invalid"),
    EXPIRED_TOKEN(3009, HttpStatus.BAD_REQUEST, "Token is expired"),
    NOT_SUPPORTED_TOKEN(3010,HttpStatus.BAD_REQUEST, "지원되지 않는 JWT 토큰입니다"),

    // Influencer
    NOT_FOUND_CELEB(4001, HttpStatus.NOT_FOUND, "셀럽을 찾을 수 없습니다."),


    // Clothes
    NOT_FOUND_CLOTHES(4001, HttpStatus.NOT_FOUND, "의류를 찾을 수 없습니다."),
    ERROR_UPLOAD_CLOTHES(4002, HttpStatus.BAD_REQUEST, "의류를 등록하는데 실패했습니다."),

    // Post
    NOT_FOUND_POST(6001, HttpStatus.NOT_FOUND, "포스트를 찾을 수 없습니다."),


    INTERNAL_ERROR(5000, HttpStatus.INTERNAL_SERVER_ERROR, "Internal error"),
    ;

    private final Integer code;
    private final HttpStatus httpStatus;
    private final String message;

    public String getMessage(Throwable e) {
//        return this.getMessage(this.getMessage() + " - " + e.getMessage());
        return this.getMessage(e.getMessage());
    }

    public String getMessage(String message) {
        return Optional.ofNullable(message)
            .filter(Predicate.not(String::isBlank))
            .orElse(this.getMessage());
    }

    public static Code valueOf(HttpStatus httpStatus) {
        if (httpStatus == null) {
            throw new GeneralException("HttpStatus is null.");
        }

        return Arrays.stream(values())
            .filter(errorCode -> errorCode.getHttpStatus() == httpStatus)
            .findFirst()
            .orElseGet(() -> {
                if (httpStatus.is4xxClientError()) {
                    return Code.BAD_REQUEST;
                } else if (httpStatus.is5xxServerError()) {
                    return Code.INTERNAL_ERROR;
                } else {
                    return Code.OK;
                }
            });
    }

    @Override
    public String toString() {
        return String.format("%s (%d)", this.name(), this.getCode());
    }
}