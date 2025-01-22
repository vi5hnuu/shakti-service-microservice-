package com.vi5hnu.app_service.Entity.user;

import com.vi5hnu.app_service.Dto.AartiDto;
import com.vi5hnu.app_service.Dto.UserDto;
import com.vi5hnu.app_service.Dto.UserRole;
import com.vi5hnu.app_service.enums.AccountType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.sql.Timestamp;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = AartiModel.TABLE_NAME)
public class AartiModel {
    public final static String TABLE_NAME = "aarti";

    @Id
    private String id;

    @Column(name = "title",nullable = false)
    private String title;

    @Column(name = "audio_url",nullable = false)
    private String audioUrl;

    @CreationTimestamp
    @Column(name = "created_at") private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at") private Timestamp updatedAt;

    public static AartiDto toDto(AartiModel aartiModel){
        return AartiDto.builder()
                .id(aartiModel.getId())
                .title(aartiModel.getTitle())
                .audioUrl(aartiModel.getAudioUrl())
                .createdAt(aartiModel.getCreatedAt())
                .updatedAt(aartiModel.getUpdatedAt())
                .build();
    }
}