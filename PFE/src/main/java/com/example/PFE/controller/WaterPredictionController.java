package com.example.PFE.controller;

import com.example.PFE.dto.WaterPredictionResponse;
import com.example.PFE.service.WaterPredictionService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/water/prediction")
public class WaterPredictionController {

    private final WaterPredictionService waterPredictionService;

    public WaterPredictionController(WaterPredictionService waterPredictionService) {
        this.waterPredictionService = waterPredictionService;
    }

    @GetMapping("/next-day")
    public WaterPredictionResponse predictNextDay(
            @RequestParam(defaultValue = "ESP32S3_EAU_01") String deviceId
    ) {
        return waterPredictionService.predictNextDay(deviceId);
    }
}