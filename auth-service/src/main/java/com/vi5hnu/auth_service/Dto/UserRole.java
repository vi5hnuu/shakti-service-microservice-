package com.vi5hnu.auth_service.Dto;

import lombok.Getter;
import org.springframework.security.core.userdetails.User;

import java.util.List;

@Getter
public enum UserRole {
    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_USER("ROLE_USER");

    UserRole(String name){
        this.name=name;
    }
    private final String name;

    public static UserRole[] getAll(){
        return UserRole.values();
    }
}