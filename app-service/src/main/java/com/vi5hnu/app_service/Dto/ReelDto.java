package com.vi5hnu.app_service.Dto;

import lombok.*;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ReelDto {
    private String id;
    private String title;
    private String videoUrl;
    private String description;
    private ReelCategoryDto category;
    private boolean isActive;
    private String thumbnailUrl;
    private Timestamp createdAt;
}