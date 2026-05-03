package com.example.PFE.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document("machines")
public class Machine {
    @Id
    private String id;
    private String code;         // ex: M-01
    private String nom;          // ex: Jigger 1
    private boolean actif;
    private boolean deleted=false; // soft delete
    private Date createdAt=new Date();
    // derniers valeurs (test manuel pour le moment)
    private Double lastWaterLiters;     // eau (L)
    private Double lastCurrentWatts;    // courant (W) أو استعمل Amp إذا تحب


    private Date updatedAt;

    public void setLastWaterLiters(Double lastWaterLiters) {
        this.lastWaterLiters = lastWaterLiters;
    }

    public void setLastCurrentWatts(Double lastCurrentWatts) {
        this.lastCurrentWatts = lastCurrentWatts;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Double getLastWaterLiters() {
        return lastWaterLiters;
    }

    public Double getLastCurrentWatts() {
        return lastCurrentWatts;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }


    public String getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getNom() {
        return nom;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public boolean isActif() {
        return actif;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
    }

}
