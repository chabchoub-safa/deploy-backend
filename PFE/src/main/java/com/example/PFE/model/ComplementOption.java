package com.example.PFE.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "complement_options")
public class ComplementOption {
    @Id
    private String id;

    private String nom;
    private boolean deleted = false;

    public ComplementOption() {}

    public ComplementOption(String nom) {
        this.nom = nom;
    }

    public String getId() { return id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public boolean isDeleted() { return deleted; }
    public void setDeleted(boolean deleted) { this.deleted = deleted; }
}