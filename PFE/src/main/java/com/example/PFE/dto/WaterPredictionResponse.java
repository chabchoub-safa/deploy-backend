package com.example.PFE.dto;

public class WaterPredictionResponse {
    private String deviceId;
    private double predictedWaterLiters;
    private String predictionDate;
    private String modelName;

    public WaterPredictionResponse() {
    }

    public WaterPredictionResponse(String deviceId, double predictedWaterLiters, String predictionDate, String modelName) {
        this.deviceId = deviceId;
        this.predictedWaterLiters = predictedWaterLiters;
        this.predictionDate = predictionDate;
        this.modelName = modelName;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public double getPredictedWaterLiters() {
        return predictedWaterLiters;
    }

    public void setPredictedWaterLiters(double predictedWaterLiters) {
        this.predictedWaterLiters = predictedWaterLiters;
    }

    public String getPredictionDate() {
        return predictionDate;
    }

    public void setPredictionDate(String predictionDate) {
        this.predictionDate = predictionDate;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }
}