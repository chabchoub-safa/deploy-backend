package com.example.PFE.service;

import com.example.PFE.model.AssTech;
import com.example.PFE.repository.AssTechRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssTechService {

    private final AssTechRepository repo;

    public AssTechService(AssTechRepository repo) {
        this.repo = repo;
    }

    public List<AssTech> getAll(String q) {
        if (q == null || q.trim().isEmpty()) {
            return repo.findAll();
        }

        String s = q.trim();
        return repo.findByEntrepriseContainingIgnoreCaseOrObjetContainingIgnoreCaseOrCatContainingIgnoreCaseOrDossierItpContainingIgnoreCaseOrObservationsContainingIgnoreCase(
                s, s, s, s, s
        );
    }

    public AssTech getById(String id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Ass Tech introuvable avec id : " + id));
    }

    public AssTech create(AssTech item) {
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

    public AssTech update(String id, AssTech updated) {
        AssTech current = getById(id);

        current.setCat(updated.getCat());
        current.setEntreprise(updated.getEntreprise());
        current.setObjet(updated.getObjet());
        current.setHj(updated.getHj());
        current.setCaDt(updated.getCaDt());
        current.setDevis(updated.getDevis());
        current.setDate(updated.getDate());
        current.setDateSig(updated.getDateSig());
        current.setDateInterv(updated.getDateInterv());
        current.setDateFinPrev(updated.getDateFinPrev());
        current.setPourcentageAv(updated.getPourcentageAv());
        current.setDossierItp(updated.getDossierItp());

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
        current.setNb(updated.getNb());
        current.setAutre(updated.getAutre());
        current.setFacture(updated.getFacture());
        current.setDateFacture(updated.getDateFacture());
        current.setObservations(updated.getObservations());

        return repo.save(current);
    }

    public void delete(String id) {
        AssTech current = getById(id);
        repo.delete(current);
    }
}