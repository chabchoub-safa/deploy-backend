//package com.example.PFE.dto;
//
//public class HjSummaryRow {
//
//    private String cat;
//    private String nature;
//
//    private double abdlhmid = 0;
//    private double insaf = 0;
//    private double rachida = 0;
//    private double majdi = 0;
//    private double chourouk = 0;
//
//    public HjSummaryRow() {
//    }
//
//    public HjSummaryRow(String cat, String nature) {
//        this.cat = cat;
//        this.nature = nature;
//    }
//
//    public String getCat() {
//        return cat;
//    }
//
//    public void setCat(String cat) {
//        this.cat = cat;
//    }
//
//    public String getNature() {
//        return nature;
//    }
//
//    public void setNature(String nature) {
//        this.nature = nature;
//    }
//
//    public double getAbdlhmid() {
//        return abdlhmid;
//    }
//
//    public void setAbdlhmid(double abdlhmid) {
//        this.abdlhmid = abdlhmid;
//    }
//
//    public double getInsaf() {
//        return insaf;
//    }
//
//    public void setInsaf(double insaf) {
//        this.insaf = insaf;
//    }
//
//    public double getRachida() {
//        return rachida;
//    }
//
//    public void setRachida(double rachida) {
//        this.rachida = rachida;
//    }
//
//    public double getMajdi() {
//        return majdi;
//    }
//
//    public void setMajdi(double majdi) {
//        this.majdi = majdi;
//    }
//
//    public double getChourouk() {
//        return chourouk;
//    }
//
//    public void setChourouk(double chourouk) {
//        this.chourouk = chourouk;
//    }
//}

package com.example.PFE.dto;

import java.util.LinkedHashMap;
import java.util.Map;

public class HjSummaryRow {

    private String cat;
    private String nature;

    private Map<String, Double> valeurs = new LinkedHashMap<>();

    public HjSummaryRow() {}

    public HjSummaryRow(String cat, String nature) {
        this.cat = cat;
        this.nature = nature;
    }

    public String getCat() { return cat; }
    public void setCat(String cat) { this.cat = cat; }

    public String getNature() { return nature; }
    public void setNature(String nature) { this.nature = nature; }

    public Map<String, Double> getValeurs() { return valeurs; }
    public void setValeurs(Map<String, Double> valeurs) { this.valeurs = valeurs; }

    public void addValeur(String nom, double valeur) {
        valeurs.put(nom, valeurs.getOrDefault(nom, 0.0) + valeur);
    }
}