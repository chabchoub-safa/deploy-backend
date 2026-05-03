package com.example.PFE.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;
import org.springframework.data.annotation.Transient;

@Document(collection ="utilisateurs")
public class Utilisateur {
    @Id
    private String id;

    private String nom;
    private String prenom;
    private String email;
    private String password;
    @Transient
    private String confirmPassword;
    private String pays;
    private String numero; // numéro téléphone
   // private Boolean isNotRobot;// "Je ne suis pas un robot"
    private String role;



    private LocalDate dateNaissance;
    private String resetCode;



    private Long resetCodeExpiration;

    public Utilisateur() {}

    public Utilisateur(String nom, String prenom, String email, String password, String confirmPassword,
                       String pays, String numero, boolean isNotRobot, LocalDate dateNaissance, String role) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.pays = pays;
        this.numero = numero;
        //this.isNotRobot = isNotRobot;
        this.dateNaissance = dateNaissance;
        this.role = role;
    }

    // Getters & Setters

    public String getId() { return id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getConfirmPassword() { return confirmPassword; }
    public void setConfirmPassword(String confirmPassword) { this.confirmPassword = confirmPassword; }

    public String getPays() { return pays; }
    public void setPays(String pays) { this.pays = pays; }

    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }

    public Long getResetCodeExpiration() {
        return resetCodeExpiration;
    }

    public String getResetCode() {
        return resetCode;
    }
    public void setResetCodeExpiration(Long resetCodeExpiration) {
        this.resetCodeExpiration = resetCodeExpiration;
    }

    public void setResetCode(String resetCode) {
        this.resetCode = resetCode;
    }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public LocalDate getDateNaissance() { return dateNaissance; }
    public void setDateNaissance(LocalDate dateNaissance) { this.dateNaissance = dateNaissance; }
}
