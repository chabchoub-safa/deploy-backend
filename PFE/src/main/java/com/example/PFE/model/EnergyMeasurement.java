package com.example.PFE.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "energy_measurements")
public class EnergyMeasurement {

    @Id
    private String id;

    private String deviceId;
    private double voltage;
    private double current;
    private double power;
    private double energy;
    private double frequency;
    private double powerFactor;
    private LocalDateTime receivedAt;

    public EnergyMeasurement() {
    }

    public EnergyMeasurement(String deviceId, double voltage, double current, double power,
                             double energy, double frequency, double powerFactor,
                             LocalDateTime receivedAt) {
        this.deviceId = deviceId;
        this.voltage = voltage;
        this.current = current;
        this.power = power;
        this.energy = energy;
        this.frequency = frequency;
        this.powerFactor = powerFactor;
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

    public double getVoltage() {
        return voltage;
    }

    public void setVoltage(double voltage) {
        this.voltage = voltage;
    }

    public double getCurrent() {
        return current;
    }

    public void setCurrent(double current) {
        this.current = current;
    }

    public double getPower() {
        return power;
    }

    public void setPower(double power) {
        this.power = power;
    }

    public double getEnergy() {
        return energy;
    }

    public void setEnergy(double energy) {
        this.energy = energy;
    }

    public double getFrequency() {
        return frequency;
    }

    public void setFrequency(double frequency) {
        this.frequency = frequency;
    }

    public double getPowerFactor() {
        return powerFactor;
    }

    public void setPowerFactor(double powerFactor) {
        this.powerFactor = powerFactor;
    }

    public LocalDateTime getReceivedAt() {
        return receivedAt;
    }

    public void setReceivedAt(LocalDateTime receivedAt) {
        this.receivedAt = receivedAt;
    }
}