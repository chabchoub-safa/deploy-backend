package com.example.PFE.service;

import com.example.PFE.dto.WaterPredictionResponse;
import com.example.PFE.model.WaterMeasurement;
import com.example.PFE.repository.WaterMeasurementRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class WaterPredictionService {

    private final WaterMeasurementRepository repository;
    private final RestTemplate restTemplate;

    @Value("${ai.service.url}")
    private String aiServiceUrl;

    public WaterPredictionService(WaterMeasurementRepository repository) {
        this.repository = repository;
        this.restTemplate = new RestTemplate();
    }

    public WaterPredictionResponse predictNextDay(String deviceId) {
        List<WaterMeasurement> history =
                repository.findByDeviceIdOrderByReceivedAtAsc(deviceId);
        if (history == null || history.isEmpty()) {
            throw new IllegalArgumentException("Aucune donnée disponible pour ce device.");
        }

        List<Map<String, Object>> rows = new ArrayList<>();

        for (WaterMeasurement m : history) {
            if (m.getReceivedAt() == null) {
                continue;
            }

            Map<String, Object> row = new HashMap<>();
            row.put("deviceId", m.getDeviceId());
            row.put("flowLMin", m.getFlowLMin());
            row.put("totalLiters", m.getTotalLiters());
            row.put("pulseCount", m.getPulseCount());
            row.put("receivedAt", m.getReceivedAt().toString());
            rows.add(row);
        }

        if (rows.isEmpty()) {
            throw new IllegalArgumentException("Historique invalide : aucune date reçue.");
        }

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("deviceId", deviceId);
        requestBody.put("history", rows);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                aiServiceUrl + "/predict/water/next-day",
                HttpMethod.POST,
                requestEntity,
                Map.class
        );

        Map body = response.getBody();
        if (body == null) {
            throw new IllegalStateException("Réponse vide du service IA.");
        }

        return new WaterPredictionResponse(
                String.valueOf(body.get("deviceId")),
                ((Number) body.get("predictedWaterLiters")).doubleValue(),
                String.valueOf(body.get("predictionDate")),
                String.valueOf(body.get("modelName"))
        );
    }
}