package com.vi5hnu.app_service.Entity.user;

import com.vi5hnu.app_service.Dto.ReelCategoryDto;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = ReelCategoryModel.TABLE_NAME)
public class ReelCategoryModel {
    public static final String TABLE_NAME = "reel_category";

    @Id
    private String id; // Primary key

    @Column(name = "name", nullable = false, unique = true)
    private String name; // Name of the category

    public static ReelCategoryDto toDto(ReelCategoryModel reelCategoryModel){
        if(reelCategoryModel==null) return null;
        return ReelCategoryDto.builder()
                .id(reelCategoryModel.getId())
                .name(reelCategoryModel.getName())
                .build();
    }
}