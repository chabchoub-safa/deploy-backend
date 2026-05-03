package com.example.PFE.repository;

import com.example.PFE.model.AssTech;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AssTechRepository extends MongoRepository<AssTech, String> {

    List<AssTech> findByEntrepriseContainingIgnoreCaseOrObjetContainingIgnoreCaseOrCatContainingIgnoreCaseOrDossierItpContainingIgnoreCaseOrObservationsContainingIgnoreCase(
            String entreprise,
            String objet,
            String cat,
            String dossierItp,
            String observations
    );
}