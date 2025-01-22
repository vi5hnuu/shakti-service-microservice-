package com.vi5hnu.app_service.Dto;

import lombok.*;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AartiDto {
    private String id;
    private String title;
    private String audioUrl;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}