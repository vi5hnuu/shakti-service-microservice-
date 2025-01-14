package com.vi5hnu.auth_service.exceptions;

public class UserAlreadyExistsException extends Exception{
    public UserAlreadyExistsException(String msg){
        super(msg);
    }
}
