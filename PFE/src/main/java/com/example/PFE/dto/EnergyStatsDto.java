package com.example.PFE.dto;

public class EnergyStatsDto {

    private double totalConsumption;
    private double averagePower;
    private double peakPower;
    private double averageCurrent;
    private double averageVoltage;
    private long count;

    public EnergyStatsDto() {
    }

    public EnergyStatsDto(double totalConsumption, double averagePower, double peakPower,
                          double averageCurrent, double averageVoltage, long count) {
        this.totalConsumption = totalConsumption;
        this.averagePower = averagePower;
        this.peakPower = peakPower;
        this.averageCurrent = averageCurrent;
        this.averageVoltage = averageVoltage;
        this.count = count;
    }

    public double getTotalConsumption() {
        return totalConsumption;
    }

    public void setTotalConsumption(double totalConsumption) {
        this.totalConsumption = totalConsumption;
    }

    public double getAveragePower() {
        return averagePower;
    }

    public void setAveragePower(double averagePower) {
        this.averagePower = averagePower;
    }

    public double getPeakPower() {
        return peakPower;
    }

    public void setPeakPower(double peakPower) {
        this.peakPower = peakPower;
    }

    public double getAverageCurrent() {
        return averageCurrent;
    }

    public void setAverageCurrent(double averageCurrent) {
        this.averageCurrent = averageCurrent;
    }

    public double getAverageVoltage() {
        return averageVoltage;
    }

    public void setAverageVoltage(double averageVoltage) {
        this.averageVoltage = averageVoltage;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}