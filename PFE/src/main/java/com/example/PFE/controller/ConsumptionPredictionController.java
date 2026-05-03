package com.example.PFE.controller;

import com.example.PFE.dto.PeriodPredictionResponse;
import com.example.PFE.service.ConsumptionPredictionService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/prediction")
public class ConsumptionPredictionController {

    private final ConsumptionPredictionService service;

    public ConsumptionPredictionController(ConsumptionPredictionService service) {
        this.service = service;
    }

    @GetMapping("/water/period")
    public PeriodPredictionResponse predictWaterPeriod(
            @RequestParam(defaultValue = "ESP32S3_EAU_01") String waterDeviceId,
            @RequestParam(defaultValue = "PZEM_01") String energyDeviceId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end
    ) {
        return service.predictPeriod("water", waterDeviceId, energyDeviceId, start, end);
    }

    @GetMapping("/energy/period")
    public PeriodPredictionResponse predictEnergyPeriod(
            @RequestParam(defaultValue = "ESP32S3_EAU_01") String waterDeviceId,
            @RequestParam(defaultValue = "PZEM_01") String energyDeviceId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end
    ) {
        return service.predictPeriod("energy", waterDeviceId, energyDeviceId, start, end);
    }
}