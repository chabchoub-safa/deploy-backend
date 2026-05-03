package com.example.PFE.dto;
public  class ConsumptionDTO {
    public Double waterLiters;
    public Double currentWatts;

    public void setWaterLiters(Double waterLiters) {
        this.waterLiters = waterLiters;
    }

    public void setCurrentWatts(Double currentWatts) {
        this.currentWatts = currentWatts;
    }

    public Double getWaterLiters() {
        return waterLiters;
    }

    public Double getCurrentWatts() {
        return currentWatts;
    }
}