package com.example.PFE.dto;
public class AssignTechnicienRequest {
    private String technicienId;
    private String technicienNom; // optionnel

    public String getTechnicienId() { return technicienId; }
    public void setTechnicienId(String technicienId) { this.technicienId = technicienId; }

    public String getTechnicienNom() { return technicienNom; }
    public void setTechnicienNom(String technicienNom) { this.technicienNom = technicienNom; }
}
