//package com.example.PFE.service;
//
//import com.example.PFE.model.ComplementOption;
//import com.example.PFE.repository.ComplementOptionRepository;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class ComplementOptionService {
//
//    private final ComplementOptionRepository repo;
//
//    public ComplementOptionService(ComplementOptionRepository repo) {
//        this.repo = repo;
//    }
//
//    public List<ComplementOption> getAll() {
//        return repo.findAll();
//    }
//
//    public ComplementOption create(ComplementOption item) {
//        String nom = item.getNom() == null ? "" : item.getNom().trim();
//
//        if (nom.isEmpty()) {
//            throw new RuntimeException("Le nom du complément est obligatoire");
//        }
//
//        return repo.findByNomIgnoreCase(nom)
//                .orElseGet(() -> repo.save(new ComplementOption(nom)));
//    }
//}

package com.example.PFE.service;

import com.example.PFE.model.ComplementOption;
import com.example.PFE.repository.ComplementOptionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComplementOptionService {

    private final ComplementOptionRepository repo;

    public ComplementOptionService(ComplementOptionRepository repo) {
        this.repo = repo;
    }

    public List<ComplementOption> getAll() {
        return repo.findByDeletedFalse();
    }

    public List<ComplementOption> getTrash() {
        return repo.findByDeletedTrue();
    }

    public ComplementOption create(ComplementOption item) {
        item.setDeleted(false);
        return repo.save(item);
    }

    public void softDelete(String id) {
        ComplementOption item = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Complément introuvable"));

        item.setDeleted(true);
        repo.save(item);
    }

    public void restore(String id) {
        ComplementOption item = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Complément introuvable"));

        item.setDeleted(false);
        repo.save(item);
    }
}