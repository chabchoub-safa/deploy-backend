package com.example.PFE.service;

import com.example.PFE.model.SuiviPlanAction;
import com.example.PFE.repository.SuiviPlanActionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SuiviPlanActionService {

    private final SuiviPlanActionRepository repo;

    public SuiviPlanActionService(SuiviPlanActionRepository repo) {
        this.repo = repo;
    }

    public List<SuiviPlanAction> getAll(String q) {
        if (q == null || q.trim().isEmpty()) {
            return repo.findAll();
        }

        String s = q.trim();

        return repo.findByEntrepriseContainingIgnoreCaseOrObjetContainingIgnoreCaseOrCategorieContainingIgnoreCaseOrDelaisContainingIgnoreCaseOrRealisationContainingIgnoreCase(
                s, s, s, s, s
        );
    }

    public SuiviPlanAction getById(String id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Plan d'action introuvable avec id : " + id));
    }

    public SuiviPlanAction create(SuiviPlanAction item) {
        item.setId(null);
        if (item.getComplements() != null) {
            item.setComplements(
                    item.getComplements()
                            .stream()
                            .filter(c -> c != null
                                    && c.getNom() != null
                                    && !c.getNom().trim().isEmpty()
                                    && c.getValeur() != null
                                    && !c.getValeur().trim().isEmpty())
                            .toList()
            );
        }
        return repo.save(item);
    }

    public SuiviPlanAction update(String id, SuiviPlanAction updated) {
        SuiviPlanAction current = getById(id);

        current.setCategorie(updated.getCategorie());
        current.setEntreprise(updated.getEntreprise());
        current.setObjet(updated.getObjet());
        current.setDossierRecu(updated.getDossierRecu());
        current.setHj(updated.getHj());
        current.setDecisionCopil(updated.getDecisionCopil());
        current.setDevis(updated.getDevis());
        current.setDate(updated.getDate());
        current.setDateSignature(updated.getDateSignature());
        current.setDateIntervention(updated.getDateIntervention());
        current.setDateRemiseRapport(updated.getDateRemiseRapport());
        current.setDepotBmn(updated.getDepotBmn());

        if (updated.getComplements() != null) {
            current.setComplements(
                    updated.getComplements()
                            .stream()
                            .filter(c -> c != null
                                    && c.getNom() != null
                                    && !c.getNom().trim().isEmpty()
                                    && c.getValeur() != null
                                    && !c.getValeur().trim().isEmpty())
                            .toList()
            );
        } else {
            current.setComplements(List.of());
        }
        current.setDelais(updated.getDelais());
        current.setRealisation(updated.getRealisation());
        current.setObservations(updated.getObservations());

        return repo.save(current);
    }

    public void delete(String id) {
        SuiviPlanAction current = getById(id);
        repo.delete(current);
    }
}