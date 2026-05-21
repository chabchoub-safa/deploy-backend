package com.example.PFE.repository;

import com.example.PFE.model.Formation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface FormationRepository extends MongoRepository<Formation, String> {
    long countByEntrepriseIgnoreCase(String entreprise);
    List<Formation> findByEntrepriseContainingIgnoreCaseOrThemesContainingIgnoreCaseOrCatThemesContainingIgnoreCaseOrSuitesEntreprisesContainingIgnoreCase(
            String entreprise,
            String themes,
            String catThemes,
            String suitesEntreprises
    );
}