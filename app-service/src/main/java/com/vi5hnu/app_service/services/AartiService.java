package com.vi5hnu.app_service.services;

import com.vi5hnu.app_service.Dto.AartiDto;
import com.vi5hnu.app_service.Entity.user.AartiModel;
import com.vi5hnu.app_service.exceptions.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AartiService {
    private final AartiRepository aartiRepository;

    public com.vi5hnu.app_service.commons.Pageable<AartiDto> findAllAarties(int pageNo, int pageSize) throws ApiException {//pageNo from 1 onwards
        if(pageNo<=0 || pageSize<=0) throw new ApiException(HttpStatus.BAD_REQUEST,"Invalid pageNo [1,]/count");
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize); // Page index is 0-based in Spring Data
        final var aartis=aartiRepository.findAll(pageable);
        final var totalAartis=aartiRepository.count();
        return new com.vi5hnu.app_service.commons.Pageable<>(aartis.stream().map(AartiModel::toDto).toList(),pageNo,totalAartis);
    }
}
