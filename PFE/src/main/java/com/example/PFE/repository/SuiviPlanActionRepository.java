package com.example.PFE.repository;

import com.example.PFE.model.SuiviPlanAction;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SuiviPlanActionRepository extends MongoRepository<SuiviPlanAction, String> {

    List<SuiviPlanAction> findByEntrepriseContainingIgnoreCaseOrObjetContainingIgnoreCaseOrCategorieContainingIgnoreCaseOrDelaisContainingIgnoreCaseOrRealisationContainingIgnoreCase(
            String entreprise,
            String objet,
            String categorie,
            String delais,
            String realisation
    );
}