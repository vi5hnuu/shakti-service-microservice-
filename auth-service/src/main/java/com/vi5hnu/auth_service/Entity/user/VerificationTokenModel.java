package com.vi5hnu.auth_service.Entity.user;

import com.vi5hnu.auth_service.constants.Constants;
import com.vi5hnu.auth_service.enums.TokenReason;
import com.vi5hnu.auth_service.enums.TokenStatus;
import com.vi5hnu.auth_service.utils.IdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = VerificationTokenModel.TABLE_NAME)
public class VerificationTokenModel {
    public final static String TABLE_NAME = "verification_token";
    public static final int EXPIRE_AFTER_MINS = 15 ; // 15 mins

    @Id
    private String id;
    @Column(name = "user_id")
    private String userId;

    @Column(name = "token")
    private String token;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default()
    private TokenStatus status = TokenStatus.UN_USED;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,updatable = false)
    private TokenReason reason;

    @CreationTimestamp
    @Column(name = "created_at",nullable = false,updatable = false)
    private Date createdAt;

    @Column(name = "expire_at",nullable = false, updatable = false)
    private Timestamp expireAt;

    @Column(name = "updated_at",nullable = false)
    @UpdateTimestamp
    private Timestamp updatedAt;

    public VerificationTokenModel(String userId,String token,TokenReason reason){
        this(null,userId,token,null,reason,null,null,null);
    }

    @PrePersist()
    private void beforeSave(){
        if(getId()==null) setId(IdGenerators.generateIdWithPrefix(Constants.VERIFICATION_TOKEN_ID_PREFIX));
    }
}
