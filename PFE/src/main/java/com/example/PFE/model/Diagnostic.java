package com.example.PFE.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

import java.util.Date;

@Document(collection = "diagnostics")
public class Diagnostic {

    @Id
    private String id;

    private String cat;
    private String entreprise;
    private String objet;

    private Double hj;
    private Double caDtHt;

    private String devis;

    private Date date;
    private Date dateSign;
    private Date dateInterv;
    private Date dateDemarrage;
    private Date dateFinPrev;

    private List<Complement> complements;


    private String autres;

    private String pourcentageTech;
    private String pourcentageRh;
    private String pourcentageFin;
    private String pourcentagePos;

    private Date dateDepotMan;

    private String adhesion;

    private String facture30;
    private Date dateFacture30;

    private String facture70;
    private Date dateFacture70;

    private String observations;

    public Diagnostic() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getCat() { return cat; }
    public void setCat(String cat) { this.cat = cat; }

    public String getEntreprise() { return entreprise; }
    public void setEntreprise(String entreprise) { this.entreprise = entreprise; }

    public String getObjet() { return objet; }
    public void setObjet(String objet) { this.objet = objet; }

    public Double getHj() { return hj; }
    public void setHj(Double hj) { this.hj = hj; }

    public Double getCaDtHt() { return caDtHt; }
    public void setCaDtHt(Double caDtHt) { this.caDtHt = caDtHt; }

    public String getDevis() { return devis; }
    public void setDevis(String devis) { this.devis = devis; }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    public Date getDateSign() { return dateSign; }
    public void setDateSign(Date dateSign) { this.dateSign = dateSign; }

    public Date getDateInterv() { return dateInterv; }
    public void setDateInterv(Date dateInterv) { this.dateInterv = dateInterv; }

    public Date getDateDemarrage() { return dateDemarrage; }
    public void setDateDemarrage(Date dateDemarrage) { this.dateDemarrage = dateDemarrage; }

    public Date getDateFinPrev() { return dateFinPrev; }
    public void setDateFinPrev(Date dateFinPrev) { this.dateFinPrev = dateFinPrev; }




    public String getAutres() { return autres; }
    public void setAutres(String autres) { this.autres = autres; }

    public String getPourcentageTech() { return pourcentageTech; }
    public void setPourcentageTech(String pourcentageTech) { this.pourcentageTech = pourcentageTech; }

    public String getPourcentageRh() { return pourcentageRh; }
    public void setPourcentageRh(String pourcentageRh) { this.pourcentageRh = pourcentageRh; }

    public String getPourcentageFin() { return pourcentageFin; }
    public void setPourcentageFin(String pourcentageFin) { this.pourcentageFin = pourcentageFin; }

    public String getPourcentagePos() { return pourcentagePos; }
    public void setPourcentagePos(String pourcentagePos) { this.pourcentagePos = pourcentagePos; }

    public Date getDateDepotMan() { return dateDepotMan; }
    public void setDateDepotMan(Date dateDepotMan) { this.dateDepotMan = dateDepotMan; }

    public String getAdhesion() { return adhesion; }
    public void setAdhesion(String adhesion) { this.adhesion = adhesion; }

    public String getFacture30() { return facture30; }
    public void setFacture30(String facture30) { this.facture30 = facture30; }

    public Date getDateFacture30() { return dateFacture30; }
    public void setDateFacture30(Date dateFacture30) { this.dateFacture30 = dateFacture30; }

    public String getFacture70() { return facture70; }
    public void setFacture70(String facture70) { this.facture70 = facture70; }

    public Date getDateFacture70() { return dateFacture70; }
    public void setDateFacture70(Date dateFacture70) { this.dateFacture70 = dateFacture70; }

    public String getObservations() { return observations; }
    public void setObservations(String observations) { this.observations = observations; }
    public List<Complement> getComplements() {
        return complements;
    }

    public void setComplements(List<Complement> complements) {
        this.complements = complements;
    }
}