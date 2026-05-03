package com.example.PFE.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

import java.util.Date;

@Document(collection = "ass_tech")
public class AssTech {

    @Id
    private String id;

    private String cat;
    private String entreprise;
    private String objet;

    private Double hj;
    private Double caDt;

    private String devis;

    private Date date;
    private Date dateSig;
    private Date dateInterv;
    private Date dateFinPrev;

    private String pourcentageAv;
    private String dossierItp;

    private List<Complement> complements;



    private String nb;
    private String autre;
    private String facture;

    private Date dateFacture;

    private String observations;

    public AssTech() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
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

    public Double getHj() {
        return hj;
    }

    public void setHj(Double hj) {
        this.hj = hj;
    }

    public Double getCaDt() {
        return caDt;
    }

    public void setCaDt(Double caDt) {
        this.caDt = caDt;
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

    public Date getDateSig() {
        return dateSig;
    }

    public void setDateSig(Date dateSig) {
        this.dateSig = dateSig;
    }

    public Date getDateInterv() {
        return dateInterv;
    }

    public void setDateInterv(Date dateInterv) {
        this.dateInterv = dateInterv;
    }

    public Date getDateFinPrev() {
        return dateFinPrev;
    }

    public void setDateFinPrev(Date dateFinPrev) {
        this.dateFinPrev = dateFinPrev;
    }

    public String getPourcentageAv() {
        return pourcentageAv;
    }

    public void setPourcentageAv(String pourcentageAv) {
        this.pourcentageAv = pourcentageAv;
    }

    public String getDossierItp() {
        return dossierItp;
    }

    public void setDossierItp(String dossierItp) {
        this.dossierItp = dossierItp;
    }





    public String getNb() {
        return nb;
    }

    public void setNb(String nb) {
        this.nb = nb;
    }

    public String getAutre() {
        return autre;
    }

    public void setAutre(String autre) {
        this.autre = autre;
    }

    public String getFacture() {
        return facture;
    }

    public void setFacture(String facture) {
        this.facture = facture;
    }

    public Date getDateFacture() {
        return dateFacture;
    }

    public void setDateFacture(Date dateFacture) {
        this.dateFacture = dateFacture;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public List<Complement> getComplements() {
        return complements;
    }

    public void setComplements(List<Complement> complements) {
        this.complements = complements;
    }
}