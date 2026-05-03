package com.example.PFE.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;
import java.util.Date;

@Document(collection = "suivi_itp")
public class SuiviITP {

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

//    private String abdlhmid;
//    private String insaf;
//    private String rachida;
//    private String majdi;
//    private String chourouk;

    private String observations;

    public SuiviITP() {
    }

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

//    public void setAbdlhmid(String abdlhmid) {
//        this.abdlhmid = abdlhmid;
//    }
//
//    public void setInsaf(String insaf) {
//        this.insaf = insaf;
//    }
//
//    public void setRachida(String rachida) {
//        this.rachida = rachida;
//    }
//
//    public void setMajdi(String majdi) {
//        this.majdi = majdi;
//    }
//
//    public void setChourouk(String chourouk) {
//        this.chourouk = chourouk;
//    }
//
//    public String getAbdlhmid() {
//        return abdlhmid;
//    }
//
//    public String getInsaf() {
//        return insaf;
//    }
//
//    public String getRachida() {
//        return rachida;
//    }
//
//    public String getMajdi() {
//        return majdi;
//    }
//
//    public String getChourouk() {
//        return chourouk;
//    }
//    public String getObservations() {
//        return observations;
//    }

    public void setObservations(String observations) {
        this.observations = observations;
    }
    public String getObservations() {
       return observations;
    }
}