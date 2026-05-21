package com.example.PFE.repository;

import com.example.PFE.model.SuiviITP;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SuiviITPRepository extends MongoRepository<SuiviITP, String> {
    long countByEntrepriseIgnoreCase(String entreprise);
    List<SuiviITP> findByEntrepriseContainingIgnoreCase(String entreprise);

    List<SuiviITP> findByObjetContainingIgnoreCase(String objet);

    List<SuiviITP> findByCategorieContainingIgnoreCase(String categorie);

    List<SuiviITP> findByEntrepriseContainingIgnoreCaseOrObjetContainingIgnoreCaseOrCategorieContainingIgnoreCase(
            String entreprise,
            String objet,
            String categorie
    );
}