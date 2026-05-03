package com.example.PFE.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "water_measurements")
public class WaterMeasurement {

    @Id
    private String id;

    private String deviceId;
    private double flowLMin;
    private double totalLiters;
    private long pulseCount;
    private LocalDateTime receivedAt;

    public WaterMeasurement() {
    }

    public WaterMeasurement(String deviceId, double flowLMin, double totalLiters, long pulseCount, LocalDateTime receivedAt) {
        this.deviceId = deviceId;
        this.flowLMin = flowLMin;
        this.totalLiters = totalLiters;
        this.pulseCount = pulseCount;
        this.receivedAt = receivedAt;
    }

    public String getId() {
        return id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public double getFlowLMin() {
        return flowLMin;
    }

    public void setFlowLMin(double flowLMin) {
        this.flowLMin = flowLMin;
    }

    public double getTotalLiters() {
        return totalLiters;
    }

    public void setTotalLiters(double totalLiters) {
        this.totalLiters = totalLiters;
    }

    public long getPulseCount() {
        return pulseCount;
    }

    public void setPulseCount(long pulseCount) {
        this.pulseCount = pulseCount;
    }

    public LocalDateTime getReceivedAt() {
        return receivedAt;
    }

    public void setReceivedAt(LocalDateTime receivedAt) {
        this.receivedAt = receivedAt;
    }
}