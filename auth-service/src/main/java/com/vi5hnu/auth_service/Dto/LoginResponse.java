package com.vi5hnu.auth_service.Dto;

import com.vi5hnu.auth_service.Dto.user.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private UserDto user;
    private String token;
}
