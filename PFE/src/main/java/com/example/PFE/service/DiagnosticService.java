package com.example.PFE.service;

import com.example.PFE.model.Diagnostic;
import com.example.PFE.repository.DiagnosticRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiagnosticService {

    private final DiagnosticRepository repo;

    public DiagnosticService(DiagnosticRepository repo) {
        this.repo = repo;
    }

    public List<Diagnostic> getAll(String q) {
        if (q == null || q.trim().isEmpty()) {
            return repo.findAll();
        }

        String s = q.trim();
        return repo.findByEntrepriseContainingIgnoreCaseOrObjetContainingIgnoreCaseOrCatContainingIgnoreCaseOrDevisContainingIgnoreCaseOrObservationsContainingIgnoreCase(
                s, s, s, s, s
        );
    }

    public Diagnostic getById(String id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Diagnostic introuvable avec id : " + id));
    }

    public Diagnostic create(Diagnostic item) {
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

    public Diagnostic update(String id, Diagnostic updated) {
        Diagnostic current = getById(id);

        current.setCat(updated.getCat());
        current.setEntreprise(updated.getEntreprise());
        current.setObjet(updated.getObjet());
        current.setHj(updated.getHj());
        current.setCaDtHt(updated.getCaDtHt());
        current.setDevis(updated.getDevis());
        current.setDate(updated.getDate());
        current.setDateSign(updated.getDateSign());
        current.setDateInterv(updated.getDateInterv());
        current.setDateDemarrage(updated.getDateDemarrage());
        current.setDateFinPrev(updated.getDateFinPrev());

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
        current.setPourcentageTech(updated.getPourcentageTech());
        current.setPourcentageRh(updated.getPourcentageRh());
        current.setPourcentageFin(updated.getPourcentageFin());
        current.setPourcentagePos(updated.getPourcentagePos());
        current.setDateDepotMan(updated.getDateDepotMan());
        current.setAdhesion(updated.getAdhesion());
        current.setFacture30(updated.getFacture30());
        current.setDateFacture30(updated.getDateFacture30());
        current.setFacture70(updated.getFacture70());
        current.setDateFacture70(updated.getDateFacture70());
        current.setObservations(updated.getObservations());

        return repo.save(current);
    }

    public void delete(String id) {
        Diagnostic current = getById(id);
        repo.delete(current);
    }
}