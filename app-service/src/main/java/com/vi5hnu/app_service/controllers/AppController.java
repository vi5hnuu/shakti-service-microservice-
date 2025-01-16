package com.vi5hnu.app_service.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/shakti")
@RequiredArgsConstructor
public class AppController {

    @GetMapping("/test")
    public String test(){
        return "ok";
    }
}
