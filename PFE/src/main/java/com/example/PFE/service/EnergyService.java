package com.example.PFE.service;

import com.example.PFE.dto.EnergyStatsDto;
import com.example.PFE.model.EnergyMeasurement;
import com.example.PFE.repository.EnergyMeasurementRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.DoubleSummaryStatistics;
import java.util.List;

@Service
public class EnergyService {

    private final EnergyMeasurementRepository repository;

    public EnergyService(EnergyMeasurementRepository repository) {
        this.repository = repository;
    }

    public EnergyMeasurement save(EnergyMeasurement measurement) {
        if (measurement.getReceivedAt() == null) {
            measurement.setReceivedAt(LocalDateTime.now());
        }
        return repository.save(measurement);
    }

    public List<EnergyMeasurement> getTodayHistory() {
        LocalDateTime start = LocalDate.now().atStartOfDay();
        LocalDateTime end = LocalDate.now().atTime(LocalTime.MAX);
        return repository.findByReceivedAtBetweenOrderByReceivedAtAsc(start, end);
    }

    public List<EnergyMeasurement> getHistoryBetween(LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null) {
            throw new IllegalArgumentException("Les paramètres start et end sont obligatoires.");
        }
        if (end.isBefore(start)) {
            throw new IllegalArgumentException("La date de fin doit être après la date de début.");
        }
        return repository.findByReceivedAtBetweenOrderByReceivedAtAsc(start, end);
    }

    public EnergyStatsDto getStatsBetween(LocalDateTime start, LocalDateTime end) {
        List<EnergyMeasurement> data = getHistoryBetween(start, end);

        if (data.isEmpty()) {
            return new EnergyStatsDto(0, 0, 0, 0, 0, 0);
        }

        DoubleSummaryStatistics powerStats = data.stream()
                .mapToDouble(EnergyMeasurement::getPower)
                .summaryStatistics();

        double avgCurrent = data.stream()
                .mapToDouble(EnergyMeasurement::getCurrent)
                .average()
                .orElse(0);

        double avgVoltage = data.stream()
                .mapToDouble(EnergyMeasurement::getVoltage)
                .average()
                .orElse(0);

        double firstEnergy = data.get(0).getEnergy();
        double lastEnergy = data.get(data.size() - 1).getEnergy();
        double totalConsumption = Math.max(0, lastEnergy - firstEnergy);

        return new EnergyStatsDto(
                totalConsumption,
                powerStats.getAverage(),
                powerStats.getMax(),
                avgCurrent,
                avgVoltage,
                data.size()
        );
    }
}