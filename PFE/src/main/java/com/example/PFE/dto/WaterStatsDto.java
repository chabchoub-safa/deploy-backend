package com.example.PFE.dto;

public class WaterStatsDto {

    private double totalConsumption;
    private double averageFlow;
    private double peakFlow;
    private double minFlow;
    private long totalPulses;
    private long count;

    public WaterStatsDto() {
    }

    public WaterStatsDto(double totalConsumption, double averageFlow, double peakFlow, double minFlow, long totalPulses, long count) {
        this.totalConsumption = totalConsumption;
        this.averageFlow = averageFlow;
        this.peakFlow = peakFlow;
        this.minFlow = minFlow;
        this.totalPulses = totalPulses;
        this.count = count;
    }

    public double getTotalConsumption() {
        return totalConsumption;
    }

    public void setTotalConsumption(double totalConsumption) {
        this.totalConsumption = totalConsumption;
    }

    public double getAverageFlow() {
        return averageFlow;
    }

    public void setAverageFlow(double averageFlow) {
        this.averageFlow = averageFlow;
    }

    public double getPeakFlow() {
        return peakFlow;
    }

    public void setPeakFlow(double peakFlow) {
        this.peakFlow = peakFlow;
    }

    public double getMinFlow() {
        return minFlow;
    }

    public void setMinFlow(double minFlow) {
        this.minFlow = minFlow;
    }

    public long getTotalPulses() {
        return totalPulses;
    }

    public void setTotalPulses(long totalPulses) {
        this.totalPulses = totalPulses;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}