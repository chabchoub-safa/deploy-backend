package com.example.PFE.model;

import java.util.Date;

public class DemandeLancement {
    private String numeroDemande;     // N° de Demande :contentReference[oaicite:1]{index=1}
    private String client;           // Client :contentReference[oaicite:2]{index=2}
    private String support;
    private String referenceSupportClient;
    private String Recette;
    private String ColorantDemandee;
    private String remarques;

    private Date dateReception;      // Date Reception :contentReference[oaicite:3]{index=3}
    private Date dateLancement;      // Date lancement :contentReference[oaicite:4]{index=4}
    private String Quantite;
    private String delai;

    private String Composition;
    private String Process;
    private String Disignateur;
    private String couleurEnvoyee;
    private String StandardClient;
    private String Prix;

    public void setRecette(String recette) {
        Recette = recette;
    }

    public void setColorantDemandee(String colorantDemandee) {
        ColorantDemandee = colorantDemandee;
    }

    public void setQuantite(String quantite) {
        Quantite = quantite;
    }

    public void setComposition(String composition) {
        Composition = composition;
    }

    public void setProcess(String process) {
        Process = process;
    }

    public void setDisignateur(String disignateur) {
        Disignateur = disignateur;
    }

    public void setCouleurEnvoyee(String couleurEnvoyee) {
        this.couleurEnvoyee = couleurEnvoyee;
    }

    public void setStandardClient(String standardClient) {
        StandardClient = standardClient;
    }

    public void setPrix(String prix) {
        Prix = prix;
    }

    public String getRecette() {
        return Recette;
    }

    public String getColorantDemandee() {
        return ColorantDemandee;
    }

    public String getQuantite() {
        return Quantite;
    }

    public String getComposition() {
        return Composition;
    }

    public String getProcess() {
        return Process;
    }

    public String getDisignateur() {
        return Disignateur;
    }

    public String getCouleurEnvoyee() {
        return couleurEnvoyee;
    }

    public String getStandardClient() {
        return StandardClient;
    }

    public String getPrix() {
        return Prix;
    }

    public void setNumeroDemande(String numeroDemande) {
        this.numeroDemande = numeroDemande;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public void setSupport(String support) {
        this.support = support;
    }

    public void setReferenceSupportClient(String referenceSupportClient) {
        this.referenceSupportClient = referenceSupportClient;
    }

    public void setCodeRecette(String codeRecette) {
        this.Recette = codeRecette;
    }

    public void setTypeColorant(String typeColorant) {
        this.ColorantDemandee = typeColorant;
    }

    public void setRemarques(String remarques) {
        this.remarques = remarques;
    }

    public void setDateReception(Date dateReception) {
        this.dateReception = dateReception;
    }

    public void setDateLancement(Date dateLancement) {
        this.dateLancement = dateLancement;
    }

    public void setDimensions(String dimensions) {
        this.Quantite = dimensions;
    }

    public void setDelai(String delai) {
        this.delai = delai;
    }

    public String getDelai() {
        return delai;
    }

    public String getDimensions() {
        return Quantite;
    }

    public Date getDateLancement() {
        return dateLancement;
    }

    public Date getDateReception() {
        return dateReception;
    }

    public String getRemarques() {
        return remarques;
    }

    public String getTypeColorant() {
        return ColorantDemandee;
    }

    public String getCodeRecette() {
        return Recette;
    }

    public String getReferenceSupportClient() {
        return referenceSupportClient;
    }

    public String getSupport() {
        return support;
    }

    public String getClient() {
        return client;
    }

    public String getNumeroDemande() {
        return numeroDemande;
    }
}
