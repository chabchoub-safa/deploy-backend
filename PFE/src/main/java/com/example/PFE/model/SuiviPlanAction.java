package com.example.PFE.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

import java.util.Date;

@Document(collection = "suivi_plan_action")
public class SuiviPlanAction {

    @Id
    private String id;

    private String categorie;
    private String entreprise;
    private String objet;

    private Date dossierRecu;
    private Double hj;

    private String decisionCopil;
    private String devis;

    private Date date;
    private Date dateSignature;
    private Date dateIntervention;
    private Date dateRemiseRapport;
    private Date depotBmn;

    private List<Complement> complements;

    private String delais;
    private String realisation;

    private String observations;

    public SuiviPlanAction() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getEntreprise() {
        return entreprise;
    }

    public void setEntreprise(String entreprise) {
        this.entreprise = entreprise;
    }

    public String getObjet() {
        return objet;
    }

    public void setObjet(String objet) {
        this.objet = objet;
    }

    public Date getDossierRecu() {
        return dossierRecu;
    }

    public void setDossierRecu(Date dossierRecu) {
        this.dossierRecu = dossierRecu;
    }

    public Double getHj() {
        return hj;
    }

    public void setHj(Double hj) {
        this.hj = hj;
    }

    public String getDecisionCopil() {
        return decisionCopil;
    }

    public void setDecisionCopil(String decisionCopil) {
        this.decisionCopil = decisionCopil;
    }

    public String getDevis() {
        return devis;
    }

    public void setDevis(String devis) {
        this.devis = devis;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDateSignature() {
        return dateSignature;
    }

    public void setDateSignature(Date dateSignature) {
        this.dateSignature = dateSignature;
    }

    public Date getDateIntervention() {
        return dateIntervention;
    }

    public void setDateIntervention(Date dateIntervention) {
        this.dateIntervention = dateIntervention;
    }

    public Date getDateRemiseRapport() {
        return dateRemiseRapport;
    }

    public void setDateRemiseRapport(Date dateRemiseRapport) {
        this.dateRemiseRapport = dateRemiseRapport;
    }

    public Date getDepotBmn() {
        return depotBmn;
    }

    public void setDepotBmn(Date depotBmn) {
        this.depotBmn = depotBmn;
    }

    public List<Complement> getComplements() {
        return complements;
    }

    public void setComplements(List<Complement> complements) {
        this.complements = complements;
    }

    public String getDelais() {
        return delais;
    }

    public void setDelais(String delais) {
        this.delais = delais;
    }

    public String getRealisation() {
        return realisation;
    }

    public void setRealisation(String realisation) {
        this.realisation = realisation;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }
}