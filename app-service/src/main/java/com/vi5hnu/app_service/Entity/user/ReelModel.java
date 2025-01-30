package com.vi5hnu.app_service.Entity.user;

import com.vi5hnu.app_service.Dto.AartiDto;
import com.vi5hnu.app_service.Dto.ReelDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = ReelModel.TABLE_NAME)
public class ReelModel {
    public final static String TABLE_NAME = "reels";

    @Id
    private String id;

    @Column(name = "title",nullable = false)
    private String title;

    @Column(name = "video_url",nullable = false)
    private String videoUrl;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY) // Establishes the foreign key relationship
    @JoinColumn(name = "category_id", referencedColumnName = "id") // Maps to CategoryModel's primary key
    private ReelCategoryModel category;

    @Column(name = "is_active",nullable = false)
    private boolean isActive;

    @CreationTimestamp
    @Column(name = "thumbnail_url") private String thumbnailUrl;

    @CreationTimestamp
    @Column(name = "created_at") private Timestamp createdAt;

    public static ReelDto toDto(ReelModel reelModel){
        return ReelDto.builder()
                .id(reelModel.getId())
                .title(reelModel.getTitle())
                .description(reelModel.getDescription())
                .videoUrl(reelModel.getVideoUrl())
                .category(ReelCategoryModel.toDto(reelModel.getCategory()))
                .isActive(reelModel.isActive())
                .thumbnailUrl(reelModel.getThumbnailUrl())
                .createdAt(reelModel.getCreatedAt())
                .build();
    }
}