package com.example.PFE.dto;

import com.example.PFE.model.Entreprise;
import java.util.List;

public class EntrepriseStatsDTO {

    private String id;
    private String nomEntreprise;
    private String cao;
    private String adresse;
    private String contact;
    private String specialite;
    private List<String> extensions;
    private Boolean deleted;
    private String deletedAt;
    private long nombreFormations;
    private long nombreAssTech;
    private long nombreDiagnostics;
    private long nombreITS;
    private long nombrePlansAction;
    private long totalCollaborations;

    public EntrepriseStatsDTO(
            Entreprise e,
            long nombreFormations,
            long nombreAssTech,
            long nombreDiagnostics,
            long nombreITS,
            long nombrePlansAction
    ) {
        this.id = e.getId();
        this.nomEntreprise = e.getNomEntreprise();
        this.cao = e.getCao();
        this.adresse = e.getAdresse();
        this.contact = e.getContact();
        this.specialite = e.getSpecialite();
        this.extensions = e.getExtensions();
        this.deleted = e.getDeleted();
        this.deletedAt = e.getDeletedAt() != null ? e.getDeletedAt().toString() : null;
        this.nombreFormations = nombreFormations;
        this.nombreAssTech = nombreAssTech;
        this.nombreDiagnostics = nombreDiagnostics;
        this.nombreITS = nombreITS;
        this.nombrePlansAction = nombrePlansAction;
        this.totalCollaborations =
                nombreFormations +
                        nombreAssTech +
                        nombreDiagnostics +
                        nombreITS +
                        nombrePlansAction;
    }


    public String getId() { return id; }
    public String getNomEntreprise() { return nomEntreprise; }
    public String getCao() { return cao; }
    public String getAdresse() { return adresse; }
    public String getContact() { return contact; }
    public String getSpecialite() { return specialite; }
    public List<String> getExtensions() { return extensions; }

    public long getNombreFormations() { return nombreFormations; }
    public long getNombreAssTech() { return nombreAssTech; }
    public long getNombreDiagnostics() { return nombreDiagnostics; }
    public long getNombreITS() { return nombreITS; }
    public long getNombrePlansAction() { return nombrePlansAction; }
    public long getTotalCollaborations() { return totalCollaborations; }
    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
    }
}