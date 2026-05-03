package com.example.PFE.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "rappels")
public class Rappel {

    @Id
    private String id;

    private String nom;                // nom rappel
    private String description;

    private String adminEmail;         // email administration
    private String technicienEmail;    // email personne maintenance (optionnel)

    private LocalDate prochaineDate;   // date prochain rappel (Jour J)
    private Integer frequenceJours;    // ex: 365 (chaque 365 jours)

    private Boolean done = false;      // bouton Done

    // Pour éviter envoyer plusieurs fois le même mail
    private LocalDate lastPreSent;     // date du dernier mail J-1 envoyé
    private LocalDate lastDueSent;     // date du dernier mail Jour J envoyé
    private LocalDate lastEscalSent;   // date dernière escalation

    private Date createdAt = new Date();
    private Date updatedAt = new Date();

    public void setId(String id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    public void setTechnicienEmail(String technicienEmail) {
        this.technicienEmail = technicienEmail;
    }

    public void setProchaineDate(LocalDate prochaineDate) {
        this.prochaineDate = prochaineDate;
    }

    public void setFrequenceJours(Integer frequenceJours) {
        this.frequenceJours = frequenceJours;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    public void setLastPreSent(LocalDate lastPreSent) {
        this.lastPreSent = lastPreSent;
    }

    public void setLastDueSent(LocalDate lastDueSent) {
        this.lastDueSent = lastDueSent;
    }

    public void setLastEscalSent(LocalDate lastEscalSent) {
        this.lastEscalSent = lastEscalSent;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getDescription() {
        return description;
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public String getTechnicienEmail() {
        return technicienEmail;
    }

    public LocalDate getProchaineDate() {
        return prochaineDate;
    }

    public Integer getFrequenceJours() {
        return frequenceJours;
    }

    public Boolean getDone() {
        return done;
    }

    public LocalDate getLastPreSent() {
        return lastPreSent;
    }

    public LocalDate getLastDueSent() {
        return lastDueSent;
    }

    public LocalDate getLastEscalSent() {
        return lastEscalSent;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }
}