package com.vi5hnu.app_service.services.repository;

import com.vi5hnu.app_service.Entity.user.AartiModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AartiRepository extends JpaRepository<AartiModel,String> {
}
