package com.vi5hnu.app_service.services.repository;

import com.vi5hnu.app_service.Entity.user.AartiModel;
import com.vi5hnu.app_service.Entity.user.ReelModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReelRepository extends JpaRepository<ReelModel,String> {
}
