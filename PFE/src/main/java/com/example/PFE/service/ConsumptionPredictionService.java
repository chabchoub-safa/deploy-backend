package com.example.PFE.service;

import com.example.PFE.dto.PeriodPredictionResponse;
import com.example.PFE.model.WaterMeasurement;
import com.example.PFE.model.EnergyMeasurement;
import com.example.PFE.repository.WaterMeasurementRepository;
import com.example.PFE.repository.EnergyMeasurementRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.*;

@Service
public class ConsumptionPredictionService {

    private final WaterMeasurementRepository waterRepository;
    private final EnergyMeasurementRepository energyRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${ai.service.url}")
    private String aiServiceUrl;

    public ConsumptionPredictionService(
            WaterMeasurementRepository waterRepository,
            EnergyMeasurementRepository energyRepository
    ) {
        this.waterRepository = waterRepository;
        this.energyRepository = energyRepository;
    }

    public PeriodPredictionResponse predictPeriod(
            String target,
            String waterDeviceId,
            String energyDeviceId,
            LocalDate start,
            LocalDate end
    ) {
        List<WaterMeasurement> waterHistory =
                waterRepository.findByDeviceIdOrderByReceivedAtAsc(waterDeviceId);

        List<EnergyMeasurement> energyHistory =
                energyRepository.findByDeviceIdOrderByReceivedAtAsc(energyDeviceId);

        if (waterHistory == null || waterHistory.isEmpty()) {
            throw new IllegalArgumentException("Aucune donnée eau disponible.");
        }

        if (energyHistory == null || energyHistory.isEmpty()) {
            throw new IllegalArgumentException("Aucune donnée énergie disponible.");
        }

        List<Map<String, Object>> waterRows = new ArrayList<>();
        for (WaterMeasurement m : waterHistory) {
            if (m.getReceivedAt() == null) continue;

            Map<String, Object> row = new HashMap<>();
            row.put("deviceId", m.getDeviceId());
            row.put("flowLMin", m.getFlowLMin());
            row.put("totalLiters", m.getTotalLiters());
            row.put("pulseCount", m.getPulseCount());
            row.put("receivedAt", m.getReceivedAt().toString());
            waterRows.add(row);
        }

        List<Map<String, Object>> energyRows = new ArrayList<>();
        for (EnergyMeasurement m : energyHistory) {
            if (m.getReceivedAt() == null) continue;

            Map<String, Object> row = new HashMap<>();
            row.put("deviceId", m.getDeviceId());
            row.put("voltage", m.getVoltage());
            row.put("current", m.getCurrent());
            row.put("power", m.getPower());
            row.put("energy", m.getEnergy());
            row.put("frequency", m.getFrequency());
            row.put("powerFactor", m.getPowerFactor());
            row.put("receivedAt", m.getReceivedAt().toString());
            energyRows.add(row);
        }

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("target", target);
        requestBody.put("startDate", start.toString());
        requestBody.put("endDate", end.toString());
        requestBody.put("waterHistory", waterRows);
        requestBody.put("energyHistory", energyRows);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity =
                new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                aiServiceUrl + "/predict/period",
                HttpMethod.POST,
                entity,
                Map.class
        );

        Map body = response.getBody();

        if (body == null) {
            throw new IllegalStateException("Réponse vide du service IA.");
        }

        return new PeriodPredictionResponse(
                String.valueOf(body.get("target")),
                String.valueOf(body.get("startDate")),
                String.valueOf(body.get("endDate")),
                ((Number) body.get("predictedValue")).doubleValue(),
                String.valueOf(body.get("unit")),
                String.valueOf(body.get("modelName"))
        );
    }
}