package com.example.PFE.dto;

public class StopScanRequest {
    private String tissuId;
    private String machineId;

    public String getTissuId() { return tissuId; }
    public void setTissuId(String tissuId) { this.tissuId = tissuId; }

    public String getMachineId() { return machineId; }
    public void setMachineId(String machineId) { this.machineId = machineId; }
}