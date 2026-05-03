package com.example.PFE.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

import java.util.Date;

@Document(collection = "formations")
public class Formation {

    @Id
    private String id;

    private String catThemes;
    private String entreprise;
    private String themes;
    private String suitesEntreprises;
    private String avancement;
    private Integer nombreModulesApprouves;
    private String facture;
    private Date date;
    private Double montantDt;
    private Double hj;
    private List<Complement> complements;

    public Formation() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCatThemes() {
        return catThemes;
    }

    public void setCatThemes(String catThemes) {
        this.catThemes = catThemes;
    }

    public String getEntreprise() {
        return entreprise;
    }

    public void setEntreprise(String entreprise) {
        this.entreprise = entreprise;
    }

    public String getThemes() {
        return themes;
    }

    public void setThemes(String themes) {
        this.themes = themes;
    }

    public String getSuitesEntreprises() {
        return suitesEntreprises;
    }

    public void setSuitesEntreprises(String suitesEntreprises) {
        this.suitesEntreprises = suitesEntreprises;
    }

    public String getAvancement() {
        return avancement;
    }

    public void setAvancement(String avancement) {
        this.avancement = avancement;
    }

    public Integer getNombreModulesApprouves() {
        return nombreModulesApprouves;
    }

    public void setNombreModulesApprouves(Integer nombreModulesApprouves) {
        this.nombreModulesApprouves = nombreModulesApprouves;
    }

    public String getFacture() {
        return facture;
    }

    public void setFacture(String facture) {
        this.facture = facture;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getMontantDt() {
        return montantDt;
    }

    public void setMontantDt(Double montantDt) {
        this.montantDt = montantDt;
    }

    public Double getHj() {
        return hj;
    }

    public void setHj(Double hj) {
        this.hj = hj;
    }

    public List<Complement> getComplements() {
        return complements;
    }

    public void setComplements(List<Complement> complements) {
        this.complements = complements;
    }
}