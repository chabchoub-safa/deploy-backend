//package com.example.PFE.service;
//
//import com.example.PFE.dto.WaterStatsDto;
//import com.example.PFE.model.WaterMeasurement;
//import com.example.PFE.repository.WaterMeasurementRepository;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.LocalTime;
//import java.util.DoubleSummaryStatistics;
//import java.util.List;
//
//@Service
//public class WaterService {
//
//    private final WaterMeasurementRepository repository;
//
//    public WaterService(WaterMeasurementRepository repository) {
//        this.repository = repository;
//    }
//
//    public WaterMeasurement save(WaterMeasurement measurement) {
//        if (measurement.getReceivedAt() == null) {
//            measurement.setReceivedAt(LocalDateTime.now());
//        }
//        return repository.save(measurement);
//    }
//
//    public List<WaterMeasurement> getTodayHistory() {
//        LocalDateTime start = LocalDate.now().atStartOfDay();
//        LocalDateTime end = LocalDate.now().atTime(LocalTime.MAX);
//        return repository.findByReceivedAtBetweenOrderByReceivedAtAsc(start, end);
//    }
//
////    public List<WaterMeasurement> getHistoryBetween(LocalDateTime start, LocalDateTime end) {
////        if (start == null || end == null) {
////            throw new IllegalArgumentException("Les paramètres start et end sont obligatoires.");
////        }
////
////        if (end.isBefore(start)) {
////            throw new IllegalArgumentException("La date de fin doit être après la date de début.");
////        }
////
////        return repository.findByReceivedAtBetweenOrderByReceivedAtAsc(start, end);
////    }
//public List<WaterMeasurement> getHistoryBetween(
//        LocalDateTime start,
//        LocalDateTime end,
//        String deviceId
//) {
//    if (deviceId == null || deviceId.isBlank() || deviceId.equals("ALL")) {
//        return repository.findByTimestampBetween(start, end);
//    }
//
//    return repository.findByDeviceIdAndTimestampBetween(deviceId, start, end);
//}
//
//    public WaterStatsDto getStatsBetween(LocalDateTime start, LocalDateTime end) {
//        List<WaterMeasurement> data = getHistoryBetween(start, end);
//
//        if (data.isEmpty()) {
//            return new WaterStatsDto(0, 0, 0, 0, 0, 0);
//        }
//
//        DoubleSummaryStatistics flowStats = data.stream()
//                .mapToDouble(WaterMeasurement::getFlowLMin)
//                .summaryStatistics();
//
//        long totalPulses = data.stream()
//                .mapToLong(WaterMeasurement::getPulseCount)
//                .sum();
//
//        double firstTotal = data.get(0).getTotalLiters();
//        double lastTotal = data.get(data.size() - 1).getTotalLiters();
//
//        double totalConsumption = Math.max(0, lastTotal - firstTotal);
//
//        return new WaterStatsDto(
//                totalConsumption,
//                flowStats.getAverage(),
//                flowStats.getMax(),
//                flowStats.getMin(),
//                totalPulses,
//                data.size()
//        );
//    }
//}
package com.example.PFE.service;

import com.example.PFE.dto.WaterStatsDto;
import com.example.PFE.model.WaterMeasurement;
import com.example.PFE.repository.WaterMeasurementRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.DoubleSummaryStatistics;
import java.util.List;

@Service
public class WaterService {

    private final WaterMeasurementRepository repository;

    public WaterService(WaterMeasurementRepository repository) {
        this.repository = repository;
    }

    public WaterMeasurement save(WaterMeasurement measurement) {
        if (measurement.getReceivedAt() == null) {
            measurement.setReceivedAt(LocalDateTime.now());
        }
        return repository.save(measurement);
    }

    public List<WaterMeasurement> getTodayHistory(String deviceId) {
        LocalDateTime start = LocalDate.now().atStartOfDay();
        LocalDateTime end = LocalDate.now().atTime(LocalTime.MAX);

        return getHistoryBetween(start, end, deviceId);
    }

    public List<WaterMeasurement> getHistoryBetween(
            LocalDateTime start,
            LocalDateTime end,
            String deviceId
    ) {
        if (start == null || end == null) {
            throw new IllegalArgumentException("Les paramètres start et end sont obligatoires.");
        }

        if (end.isBefore(start)) {
            throw new IllegalArgumentException("La date de fin doit être après la date de début.");
        }

        if (deviceId == null || deviceId.isBlank() || deviceId.equals("ALL")) {
            return repository.findByReceivedAtBetweenOrderByReceivedAtAsc(start, end);
        }

        return repository.findByDeviceIdAndReceivedAtBetweenOrderByReceivedAtAsc(
                deviceId,
                start,
                end
        );
    }

    public WaterStatsDto getStatsBetween(
            LocalDateTime start,
            LocalDateTime end,
            String deviceId
    ) {
        List<WaterMeasurement> data = getHistoryBetween(start, end, deviceId);

        if (data.isEmpty()) {
            return new WaterStatsDto(0, 0, 0, 0, 0, 0);
        }

        DoubleSummaryStatistics flowStats = data.stream()
                .mapToDouble(WaterMeasurement::getFlowLMin)
                .summaryStatistics();

        long totalPulses = data.stream()
                .mapToLong(WaterMeasurement::getPulseCount)
                .sum();

        double firstTotal = data.get(0).getTotalLiters();
        double lastTotal = data.get(data.size() - 1).getTotalLiters();

        double totalConsumption = Math.max(0, lastTotal - firstTotal);

        return new WaterStatsDto(
                totalConsumption,
                flowStats.getAverage(),
                flowStats.getMax(),
                flowStats.getMin(),
                totalPulses,
                data.size()
        );
    }
}