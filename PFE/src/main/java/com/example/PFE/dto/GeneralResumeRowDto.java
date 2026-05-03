package com.example.PFE.dto;

public class GeneralResumeRowDto {
    private String cat;
    private String nature;
    private double hj;
    private double dt;
    private double total;

    public GeneralResumeRowDto() {}

    public GeneralResumeRowDto(String cat, String nature, double hj, double dt, double total) {
        this.cat = cat;
        this.nature = nature;
        this.hj = hj;
        this.dt = dt;
        this.total = total;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public double getHj() {
        return hj;
    }

    public void setHj(double hj) {
        this.hj = hj;
    }

    public double getDt() {
        return dt;
    }

    public void setDt(double dt) {
        this.dt = dt;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}