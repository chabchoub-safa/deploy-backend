package com.example.PFE.dto;

public record MachineTimeDTO(String machineId, long seconds) {
    @Override
    public String machineId() {
        return machineId;
    }

    @Override
    public long seconds() {
        return seconds;
    }
}