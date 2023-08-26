package com.arm.ore.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class HealthCheckController {

    @GetMapping("/health")
    public String getHealth() {
        return "application is up";
    }
}
