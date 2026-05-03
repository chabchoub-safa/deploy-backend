package com.example.PFE.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "messages_chat")
public class MessageChat {
    @Id
    private String id;

    private String clientEmail;
    private String senderEmail;
    private String senderRole; // ROLE_USER ou ROLE_ADMIN
    private String contenu;
    private LocalDateTime createdAt = LocalDateTime.now();
    private boolean lu = false;

    public String getId() { return id; }
    public String getClientEmail() { return clientEmail; }
    public void setClientEmail(String clientEmail) { this.clientEmail = clientEmail; }
    public String getSenderEmail() { return senderEmail; }
    public void setSenderEmail(String senderEmail) { this.senderEmail = senderEmail; }
    public String getSenderRole() { return senderRole; }
    public void setSenderRole(String senderRole) { this.senderRole = senderRole; }
    public String getContenu() { return contenu; }
    public void setContenu(String contenu) { this.contenu = contenu; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public boolean isLu() { return lu; }
    public void setLu(boolean lu) { this.lu = lu; }
}