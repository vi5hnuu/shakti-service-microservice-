package com.vi5hnu.auth_service.Entity.user;

import com.vi5hnu.auth_service.enums.OtpReason;
import com.vi5hnu.auth_service.enums.OtpStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name = OtpModel.TABLE_NAME)
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OtpModel {
    public static final String TABLE_NAME="otp";
    public static final int EXPIRE_AFTER_MINS=15;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String otp;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "expire_at", nullable = false,updatable = false)
    private Timestamp expireAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default()
    private OtpStatus status = OtpStatus.UN_USED;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,updatable = false)
    private OtpReason reason;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private Timestamp createdAt;

    @Column(name = "updated_at",nullable = false)
    @UpdateTimestamp
    private Timestamp updatedAt;
}
