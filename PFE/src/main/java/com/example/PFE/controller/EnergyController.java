//package com.example.PFE.controller;
//
//import com.example.PFE.dto.EnergyStatsDto;
//import com.example.PFE.model.EnergyMeasurement;
//import com.example.PFE.service.EnergyService;
//import org.springframework.format.annotation.DateTimeFormat;
//import org.springframework.web.bind.annotation.*;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/energy")
//public class EnergyController {
//
//    private final EnergyService energyService;
//
//    public EnergyController(EnergyService energyService) {
//        this.energyService = energyService;
//    }
//
//    @GetMapping("/history/today")
//    public List<EnergyMeasurement> getTodayHistory() {
//        return energyService.getTodayHistory();
//    }
//
//    @GetMapping("/history")
//    public List<EnergyMeasurement> getHistoryByRange(
//            @RequestParam("start")
//            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
//            LocalDateTime start,
//            @RequestParam("end")
//            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
//            LocalDateTime end
//    ) {
//        return energyService.getHistoryBetween(start, end);
//    }
//
//    @GetMapping("/stats")
//    public EnergyStatsDto getStatsByRange(
//            @RequestParam("start")
//            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
//            LocalDateTime start,
//            @RequestParam("end")
//            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
//            LocalDateTime end
//    ) {
//        return energyService.getStatsBetween(start, end);
//    }
////    @GetMapping("/monthly")
////    public EnergyStatsDto getMonthlyEnergy(
////            @RequestParam int year,
////            @RequestParam int month
////    ) {
////        LocalDateTime start = LocalDateTime.of(year, month, 1, 0, 0);
////        LocalDateTime end = start.plusMonths(1);
////
////        return energyService.getStatsBetween(start, end);
////    }
//}
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
    public List<EnergyMeasurement> getTodayHistory(
            @RequestParam(value = "deviceId", required = false) String deviceId
    ) {
        return energyService.getTodayHistory(deviceId);
    }

    @GetMapping("/history")
    public List<EnergyMeasurement> getHistoryByRange(
            @RequestParam("start")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime start,

            @RequestParam("end")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime end,

            @RequestParam(value = "deviceId", required = false)
            String deviceId
    ) {
        return energyService.getHistoryBetween(start, end, deviceId);
    }

    @GetMapping("/stats")
    public EnergyStatsDto getStatsByRange(
            @RequestParam("start")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime start,

            @RequestParam("end")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime end,

            @RequestParam(value = "deviceId", required = false)
            String deviceId
    ) {
        return energyService.getStatsBetween(start, end, deviceId);
    }
}