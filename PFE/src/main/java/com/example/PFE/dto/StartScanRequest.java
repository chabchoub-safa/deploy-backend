package com.example.PFE.dto;

public class StartScanRequest {
    private String tissuId;
    private String machineId;
    private Long estimatedMinutes;

    public String getTissuId() { return tissuId; }
    public void setTissuId(String tissuId) { this.tissuId = tissuId; }

    public String getMachineId() { return machineId; }
    public void setMachineId(String machineId) { this.machineId = machineId; }

    public Long getEstimatedMinutes() { return estimatedMinutes; }
    public void setEstimatedMinutes(Long estimatedMinutes) { this.estimatedMinutes = estimatedMinutes; }
}