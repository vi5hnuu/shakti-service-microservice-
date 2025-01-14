package com.vi5hnu.auth_service.events.authEvents;

import com.vi5hnu.auth_service.Dto.user.UserDto;
import com.vi5hnu.auth_service.Entity.user.UserModel;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class PasswordUpdateInit extends ApplicationEvent {
    private final UserDto user;
    private final String otp;

    public PasswordUpdateInit(UserDto user,String otp) {
        super(user);
        this.user=user;
        this.otp=otp;
    }
}