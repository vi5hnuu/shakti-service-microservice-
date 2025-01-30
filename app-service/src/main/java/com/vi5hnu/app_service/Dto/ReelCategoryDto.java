package com.vi5hnu.app_service.Dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ReelCategoryDto {
    private String id; // Primary key
    private String name; // Name of the category
}