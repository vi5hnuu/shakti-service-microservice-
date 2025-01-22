package com.vi5hnu.app_service.controllers;

import com.vi5hnu.app_service.Dto.AartiDto;
import com.vi5hnu.app_service.commons.Pageable;
import com.vi5hnu.app_service.exceptions.ApiException;
import com.vi5hnu.app_service.services.AartiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/aarti")
@RequiredArgsConstructor
public class AartiController {
    private final AartiService aartiService;
    @GetMapping(path = "all")
    public ResponseEntity<Map<String,Object>> getAartis(@RequestParam(name = "pageNo",defaultValue = "1") int pageNo, @RequestParam(name = "pageSize",defaultValue = "10") int count) throws ApiException {
        final Pageable<AartiDto> aarties = aartiService.findAllAarties(pageNo, count);
        return ResponseEntity.ok(Map.of("success",true,"data",aarties));
    }
}
