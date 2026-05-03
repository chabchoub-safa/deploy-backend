package com.example.PFE.dto;

public class PeriodPredictionResponse {
    private String target;
    private String startDate;
    private String endDate;
    private double predictedValue;
    private String unit;
    private String modelName;

    public PeriodPredictionResponse() {}

    public PeriodPredictionResponse(String target, String startDate, String endDate,
                                    double predictedValue, String unit, String modelName) {
        this.target = target;
        this.startDate = startDate;
        this.endDate = endDate;
        this.predictedValue = predictedValue;
        this.unit = unit;
        this.modelName = modelName;
    }

    public String getTarget() { return target; }
    public String getStartDate() { return startDate; }
    public String getEndDate() { return endDate; }
    public double getPredictedValue() { return predictedValue; }
    public String getUnit() { return unit; }
    public String getModelName() { return modelName; }
}