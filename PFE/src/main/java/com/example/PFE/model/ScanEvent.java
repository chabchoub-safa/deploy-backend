package com.example.PFE.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.example.PFE.model.Tissu;
import java.util.Date;

@Document("scan_events")
public class ScanEvent {
    @Id
    private String id;

    private String tissuId;
    private String machineId;
    private String technicianEmail;
    private ScanAction action;
    private Date ts = new Date();

    private Long estimatedMinutes; // uniquement pour START
    private Date expectedStopAt;   // ts + estimatedMinutes
    private boolean closed = false; // START fermé après STOP
    private String note;

    private boolean reminderEmailSent = false;

    public boolean isReminderEmailSent() {
        return reminderEmailSent;
    }

    public void setReminderEmailSent(boolean reminderEmailSent) {
        this.reminderEmailSent = reminderEmailSent;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTissuId() { return tissuId; }
    public void setTissuId(String tissuId) { this.tissuId = tissuId; }

    public String getMachineId() { return machineId; }
    public void setMachineId(String machineId) { this.machineId = machineId; }

    public String getTechnicianEmail() { return technicianEmail; }
    public void setTechnicianEmail(String technicianEmail) { this.technicianEmail = technicianEmail; }

    public ScanAction getAction() { return action; }
    public void setAction(ScanAction action) { this.action = action; }

    public Date getTs() { return ts; }
    public void setTs(Date ts) { this.ts = ts; }

    public Long getEstimatedMinutes() { return estimatedMinutes; }
    public void setEstimatedMinutes(Long estimatedMinutes) { this.estimatedMinutes = estimatedMinutes; }

    public Date getExpectedStopAt() { return expectedStopAt; }
    public void setExpectedStopAt(Date expectedStopAt) { this.expectedStopAt = expectedStopAt; }

    public boolean isClosed() { return closed; }
    public void setClosed(boolean closed) { this.closed = closed; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
}