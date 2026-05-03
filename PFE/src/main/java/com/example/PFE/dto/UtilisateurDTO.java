package com.example.PFE.dto;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class UtilisateurDTO {
    private String nom;
    private String prenom;
    private String email;
    private String password;
    private String confirmPassword;
    private String pays;
    private String numero;
    //private Boolean isNotRobot;
    private LocalDate dateNaissance;
    private String role;

    // Getters et setters

    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getPays() {
        return pays;
    }
    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getNumero() {
        return numero;
    }
    public void setNumero(String numero) {
        this.numero = numero;
    }



    public LocalDate getDateNaissance() {
        return dateNaissance;
    }
    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
}
