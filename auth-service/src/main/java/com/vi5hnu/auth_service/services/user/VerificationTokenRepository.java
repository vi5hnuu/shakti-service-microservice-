package com.vi5hnu.auth_service.services.user;

import com.vi5hnu.auth_service.Entity.user.VerificationTokenModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface VerificationTokenRepository extends JpaRepository<VerificationTokenModel,Long>, JpaSpecificationExecutor<VerificationTokenModel> {
}
