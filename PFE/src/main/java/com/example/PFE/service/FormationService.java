package com.example.PFE.service;

import com.example.PFE.model.Formation;
import com.example.PFE.repository.FormationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FormationService {

    private final FormationRepository repo;

    public FormationService(FormationRepository repo) {
        this.repo = repo;
    }

    public List<Formation> getAll(String q) {
        if (q == null || q.trim().isEmpty()) {
            return repo.findAll();
        }

        String s = q.trim();
        return repo.findByEntrepriseContainingIgnoreCaseOrThemesContainingIgnoreCaseOrCatThemesContainingIgnoreCaseOrSuitesEntreprisesContainingIgnoreCase(
                s, s, s, s
        );
    }

    public Formation getById(String id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Formation introuvable avec id : " + id));
    }

    public Formation create(Formation item) {
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

    public Formation update(String id, Formation updated) {
        Formation current = getById(id);

        current.setCatThemes(updated.getCatThemes());
        current.setEntreprise(updated.getEntreprise());
        current.setThemes(updated.getThemes());
        current.setSuitesEntreprises(updated.getSuitesEntreprises());
        current.setAvancement(updated.getAvancement());
        current.setNombreModulesApprouves(updated.getNombreModulesApprouves());
        current.setFacture(updated.getFacture());
        current.setDate(updated.getDate());
        current.setMontantDt(updated.getMontantDt());
        current.setHj(updated.getHj());

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

        return repo.save(current);
    }

    public void delete(String id) {
        Formation current = getById(id);
        repo.delete(current);
    }
}