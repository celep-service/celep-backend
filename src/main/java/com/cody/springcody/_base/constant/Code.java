package com.cody.springcody._base.constant;

import com.cody.springcody._base.exception.GeneralException;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum Code {
    OK(0, HttpStatus.OK, "Ok"),
    BAD_REQUEST(2000, HttpStatus.BAD_REQUEST, "Bad request"),
    VALIDATION_ERROR(2001, HttpStatus.BAD_REQUEST, "Validation error"),

    TRANSACTION_NOT_FOUND(3001, HttpStatus.BAD_REQUEST, "해당 인덱스의 거래내역이 없습니다."),
    ACCOUNT_TRANSACTION_NOT_FOUND(3002, HttpStatus.BAD_REQUEST, "해당 계좌의 거래내역이 없습니다."),

    INTERNAL_ERROR(5000, HttpStatus.INTERNAL_SERVER_ERROR, "Internal error"),
    ;

    private final Integer code;
    private final HttpStatus httpStatus;
    private final String message;

    public String getMessage(Throwable e) {
        return this.getMessage(this.getMessage() + " - " + e.getMessage());
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