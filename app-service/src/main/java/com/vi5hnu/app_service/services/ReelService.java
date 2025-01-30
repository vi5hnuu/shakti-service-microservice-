package com.vi5hnu.app_service.services;

import com.vi5hnu.app_service.Dto.AartiDto;
import com.vi5hnu.app_service.Dto.ReelDto;
import com.vi5hnu.app_service.Entity.user.AartiModel;
import com.vi5hnu.app_service.Entity.user.ReelModel;
import com.vi5hnu.app_service.exceptions.ApiException;
import com.vi5hnu.app_service.services.repository.AartiRepository;
import com.vi5hnu.app_service.services.repository.ReelCategoryRepository;
import com.vi5hnu.app_service.services.repository.ReelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReelService {
    private final ReelRepository reelRepository;
    private final ReelCategoryRepository reelCategoryRepository;

    public com.vi5hnu.app_service.commons.Pageable<ReelDto> findAllReels(int pageNo, int pageSize) throws ApiException {//pageNo from 1 onwards
        if(pageNo<=0 || pageSize<=0) throw new ApiException(HttpStatus.BAD_REQUEST,"Invalid pageNo [1,]/count");
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize); // Page index is 0-based in Spring Data
        final var reels=reelRepository.findAll(pageable);
        final var totalReels=reelRepository.count();
        return new com.vi5hnu.app_service.commons.Pageable<>(reels.stream().map(ReelModel::toDto).toList(),pageNo,totalReels);
    }
}
