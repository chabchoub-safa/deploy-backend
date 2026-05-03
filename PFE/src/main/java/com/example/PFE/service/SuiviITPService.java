//package com.example.PFE.service;
//
//import com.example.PFE.model.SuiviITP;
//import com.example.PFE.repository.SuiviITPRepository;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class SuiviITPService {
//
//    private final SuiviITPRepository repo;
//
//    public SuiviITPService(SuiviITPRepository repo) {
//        this.repo = repo;
//    }
//
//    public List<SuiviITP> getAll(String q) {
//        if (q == null || q.trim().isEmpty()) {
//            return repo.findAll();
//        }
//
//        String search = q.trim();
//        return repo.findByEntrepriseContainingIgnoreCaseOrObjetContainingIgnoreCaseOrCategorieContainingIgnoreCase(
//                search, search, search
//        );
//    }
//
//    public SuiviITP getById(String id) {
//        return repo.findById(id)
//                .orElseThrow(() -> new RuntimeException("Suivi ITP introuvable avec id : " + id));
//    }
//
//    public SuiviITP create(SuiviITP item) {
//        item.setId(null); // pour laisser Mongo générer l'id
//        return repo.save(item);
//    }
//
//    public SuiviITP update(String id, SuiviITP updated) {
//        SuiviITP current = getById(id);
//
//        current.setCategorie(updated.getCategorie());
//        current.setEntreprise(updated.getEntreprise());
//        current.setObjet(updated.getObjet());
//        current.setDossierRecu(updated.getDossierRecu());
//        current.setHj(updated.getHj());
//        current.setDecisionCopil(updated.getDecisionCopil());
//        current.setDevis(updated.getDevis());
//        current.setDate(updated.getDate());
//        current.setDateSignature(updated.getDateSignature());
//        current.setDateIntervention(updated.getDateIntervention());
//        current.setDateRemiseRapport(updated.getDateRemiseRapport());
//        current.setDepotBmn(updated.getDepotBmn());
//        current.setAbdlhmid(updated.getAbdlhmid());
//        current.setInsaf(updated.getInsaf());
//        current.setRachida(updated.getRachida());
//        current.setChourouk(updated.getChourouk());
//        current.setMajdi(updated.getMajdi());
//        current.setObservations(updated.getObservations());
//
//        return repo.save(current);
//    }
//
//    public void delete(String id) {
//        SuiviITP current = getById(id);
//        repo.delete(current);
//    }
//}
package com.example.PFE.service;

import com.example.PFE.model.SuiviITP;
import com.example.PFE.repository.SuiviITPRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SuiviITPService {

    private final SuiviITPRepository repo;

    public SuiviITPService(SuiviITPRepository repo) {
        this.repo = repo;
    }

    public List<SuiviITP> getAll(String q) {
        if (q == null || q.trim().isEmpty()) {
            return repo.findAll();
        }

        String search = q.trim();

        return repo.findByEntrepriseContainingIgnoreCaseOrObjetContainingIgnoreCaseOrCategorieContainingIgnoreCase(
                search,
                search,
                search
        );
    }

    public SuiviITP getById(String id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Suivi ITP introuvable avec id : " + id));
    }

    public SuiviITP create(SuiviITP item) {
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

    public SuiviITP update(String id, SuiviITP updated) {
        SuiviITP current = getById(id);

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

        current.setObservations(updated.getObservations());

        return repo.save(current);
    }

    public void delete(String id) {
        SuiviITP current = getById(id);
        repo.delete(current);
    }
}