package com.example.PFE.controller;

import com.example.PFE.dto.EnergyStatsDto;
import com.example.PFE.model.EnergyMeasurement;
import com.example.PFE.service.EnergyService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/energy")
public class EnergyController {

    private final EnergyService energyService;

    public EnergyController(EnergyService energyService) {
        this.energyService = energyService;
    }

    @GetMapping("/history/today")
    public List<EnergyMeasurement> getTodayHistory() {
        return energyService.getTodayHistory();
    }

    @GetMapping("/history")
    public List<EnergyMeasurement> getHistoryByRange(
            @RequestParam("start")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime start,
            @RequestParam("end")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime end
    ) {
        return energyService.getHistoryBetween(start, end);
    }

    @GetMapping("/stats")
    public EnergyStatsDto getStatsByRange(
            @RequestParam("start")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime start,
            @RequestParam("end")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime end
    ) {
        return energyService.getStatsBetween(start, end);
    }
}