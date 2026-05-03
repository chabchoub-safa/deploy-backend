package com.example.PFE.dto;

public class WaterPayload {

    private String deviceId;
    private double flowLMin;
    private double totalLiters;
    private long pulseCount;
    private Long timestamp;

    public WaterPayload() {
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

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}