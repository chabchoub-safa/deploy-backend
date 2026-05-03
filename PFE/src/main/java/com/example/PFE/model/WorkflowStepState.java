package com.example.PFE.model;

import java.util.Date;

public class WorkflowStepState {
    private String machineId;
    private String machineName;

    // NON_COMMENCE | EN_COURS | TERMINE
    private String status = "NON_COMMENCE";

    private Date startedAt;
    private Date stoppedAt;

    private Long estimatedMinutes; // saisi au START
    private Date reminderAt;       // startedAt + estimatedMinutes

    private String startedBy;
    private String stoppedBy;

    private String startedByEmail;
    private String startedByName;

    public void setStartedByEmail(String startedByEmail) {
        this.startedByEmail = startedByEmail;
    }

    public void setStartedByName(String startedByName) {
        this.startedByName = startedByName;
    }

    public String getStartedByName() {
        return startedByName;
    }

    public String getStartedByEmail() {
        return startedByEmail;
    }

    public String getMachineId() { return machineId; }
    public void setMachineId(String machineId) { this.machineId = machineId; }

    public String getMachineName() { return machineName; }
    public void setMachineName(String machineName) { this.machineName = machineName; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Date getStartedAt() { return startedAt; }
    public void setStartedAt(Date startedAt) { this.startedAt = startedAt; }

    public Date getStoppedAt() { return stoppedAt; }
    public void setStoppedAt(Date stoppedAt) { this.stoppedAt = stoppedAt; }

    public Long getEstimatedMinutes() { return estimatedMinutes; }
    public void setEstimatedMinutes(Long estimatedMinutes) { this.estimatedMinutes = estimatedMinutes; }

    public Date getReminderAt() { return reminderAt; }
    public void setReminderAt(Date reminderAt) { this.reminderAt = reminderAt; }

    public String getStartedBy() { return startedBy; }
    public void setStartedBy(String startedBy) { this.startedBy = startedBy; }

    public String getStoppedBy() { return stoppedBy; }
    public void setStoppedBy(String stoppedBy) { this.stoppedBy = stoppedBy; }
}