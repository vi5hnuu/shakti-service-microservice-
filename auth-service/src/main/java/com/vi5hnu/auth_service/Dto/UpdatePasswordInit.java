package com.vi5hnu.auth_service.Dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UpdatePasswordInit {
    @NotBlank(message = "userId cannot be blank") private String usernameEmail;
}
