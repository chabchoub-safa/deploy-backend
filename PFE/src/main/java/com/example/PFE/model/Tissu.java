package com.example.PFE.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Document("tissus")
public class Tissu {
    @Id
    private String id;
    private String code;          // ex: T-2026-001 (unique)
    private StatutTissu statut = StatutTissu.EN_STOCK;

    private DemandeLancement demande;
    private Date createdAt=new Date();
    private Date updatedAt=new Date();
    // ✅ Workflow : liste des machines prévues (ordre)
    private List<String> routeMachineIds = new ArrayList<>();

    // ✅ étape actuelle (index dans routeMachineIds)
    private Integer currentStepIndex = 0;

    // (optionnel) quand livré
    private Date deliveredAt;



    private String clientEmail;

    private String clientId;           // id utilisateur client
    private String clientNom;          // optionnel (nom+prenom pour affichage rapide)

    private String lastTechnicienId;   // id utilisateur technicien qui a scanné
    private String lastTechnicienNom;  // optionnel

    private List<WorkflowStepState> workflowStates;

    public void setWorkflowStates(List<WorkflowStepState> workflowStates) {
        this.workflowStates = workflowStates;
    }

    public List<WorkflowStepState> getWorkflowStates() {
        return workflowStates;
    }

    public String getClientEmail() { return clientEmail; }
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
    public void setClientEmail(String clientEmail) {
        this.clientEmail = (clientEmail == null) ? null : clientEmail.toLowerCase();
    }
    public void setClientNom(String clientNom) {
        this.clientNom = clientNom;
    }

    public void setLastTechnicienId(String lastTechnicienId) {
        this.lastTechnicienId = lastTechnicienId;
    }

    public void setLastTechnicienNom(String lastTechnicienNom) {
        this.lastTechnicienNom = lastTechnicienNom;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientNom() {
        return clientNom;
    }

    public String getLastTechnicienId() {
        return lastTechnicienId;
    }

    public String getLastTechnicienNom() {
        return lastTechnicienNom;
    }

    public void setRouteMachineIds(List<String> routeMachineIds) {
        this.routeMachineIds = routeMachineIds;
    }

    public void setCurrentStepIndex(Integer currentStepIndex) {
        this.currentStepIndex = currentStepIndex;
    }

    public void setDeliveredAt(Date deliveredAt) {
        this.deliveredAt = deliveredAt;
    }

    public List<String> getRouteMachineIds() {
        return routeMachineIds;
    }

    public Integer getCurrentStepIndex() {
        return currentStepIndex;
    }

    public Date getDeliveredAt() {
        return deliveredAt;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setStatut(StatutTissu statut) {
        this.statut = statut;
    }

    public void setDemande(DemandeLancement demande) {
        this.demande = demande;
    }

    public void setDemandeFilePath(String demandeFilePath) {
        this.demandeFilePath = demandeFilePath;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    private String demandeFilePath; // upload PDF/XLS (optionnel)

    public String getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public StatutTissu getStatut() {
        return statut;
    }

    public DemandeLancement getDemande() {
        return demande;
    }

    public String getDemandeFilePath() {
        return demandeFilePath;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }


}
