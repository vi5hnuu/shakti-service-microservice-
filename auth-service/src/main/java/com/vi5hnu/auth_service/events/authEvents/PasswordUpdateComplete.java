package com.vi5hnu.auth_service.events.authEvents;

import com.vi5hnu.auth_service.Entity.user.UserModel;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class PasswordUpdateComplete extends ApplicationEvent {
    private final UserModel userModel;

    public PasswordUpdateComplete(UserModel userModel) {
        super(userModel);
        this.userModel=userModel;
    }
}