package com.vi5hnu.auth_service.Dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UpdatePasswordInit {
    @NotBlank(message = "username/email cannot be blank") private String usernameEmail;
}
