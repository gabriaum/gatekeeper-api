package com.gabriaum.gatekeeper.infra.exception;

import org.springframework.http.HttpStatus;

public class BusinessException extends RuntimeException {
    private HttpStatus status;
    private String errorCode;

    public BusinessException(String message) {
        super(message);
        this.status = HttpStatus.BAD_REQUEST;
    }

    public BusinessException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public BusinessException(String message, String errorCode) {
        super(message);
        this.status = HttpStatus.BAD_REQUEST;
        this.errorCode = errorCode;
    }

    public BusinessException(String message, HttpStatus status, String errorCode) {
        super(message);
        this.status = status;
        this.errorCode = errorCode;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getErrorCode() {
        return errorCode;
    }
}