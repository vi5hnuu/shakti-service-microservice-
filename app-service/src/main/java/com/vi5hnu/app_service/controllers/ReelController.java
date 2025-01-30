package com.vi5hnu.app_service.controllers;

import com.vi5hnu.app_service.Dto.AartiDto;
import com.vi5hnu.app_service.Dto.ReelDto;
import com.vi5hnu.app_service.commons.Pageable;
import com.vi5hnu.app_service.exceptions.ApiException;
import com.vi5hnu.app_service.services.AartiService;
import com.vi5hnu.app_service.services.ReelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/reel")
@RequiredArgsConstructor
public class ReelController {
    private final ReelService reelService;
    @GetMapping(path = "all")
    public ResponseEntity<Map<String,Object>> getReels(@RequestParam(name = "pageNo",defaultValue = "1") int pageNo, @RequestParam(name = "pageSize",defaultValue = "10") int count) throws ApiException {
        final Pageable<ReelDto> aarties = reelService.findAllReels(pageNo, count);
        return ResponseEntity.ok(Map.of("success",true,"data",aarties));
    }
}
