package com.example.PFE.controller;

import com.example.PFE.dto.WaterStatsDto;
import com.example.PFE.model.WaterMeasurement;
import com.example.PFE.service.WaterService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/water")
public class WaterController {

    private final  WaterService waterService;

    public WaterController( WaterService waterService) {
        this.waterService = waterService;
    }

    @GetMapping("/history/today")
    public List<WaterMeasurement> getTodayHistory() {
        return waterService.getTodayHistory();
    }

    @GetMapping("/history")
    public List<WaterMeasurement> getHistoryByRange(
            @RequestParam("start")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime start,

            @RequestParam("end")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime end
    ) {
        return waterService.getHistoryBetween(start, end);
    }

    @GetMapping("/stats")
    public WaterStatsDto getStatsByRange(
            @RequestParam("start")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime start,

            @RequestParam("end")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime end
    ) {
        return waterService.getStatsBetween(start, end);
    }
//    @GetMapping("/monthly")
//    public WaterStatsDto getMonthlyWater(
//            @RequestParam int year,
//            @RequestParam int month
//    ) {
//        LocalDateTime start = LocalDateTime.of(year, month, 1, 0, 0);
//        LocalDateTime end = start.plusMonths(1);
//
//        return waterService.getStatsBetween(start, end);
//    }
}
