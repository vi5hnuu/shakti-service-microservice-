package com.vi5hnu.auth_service.Dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GoogleTokenDataDto {
    private String userId;
    private String email;
    private boolean emailVerified;
    private String name;
    private String pictureUrl;
}
