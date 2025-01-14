package com.vi5hnu.auth_service.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiException extends Exception{
    private final HttpStatus httpStatus;
    public ApiException(HttpStatus httpStatus, String msg){
        super(msg);
        this.httpStatus=httpStatus;
    }
}
