package com.example.PFE.repository;

import com.example.PFE.model.Diagnostic;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DiagnosticRepository extends MongoRepository<Diagnostic, String> {

    List<Diagnostic> findByEntrepriseContainingIgnoreCaseOrObjetContainingIgnoreCaseOrCatContainingIgnoreCaseOrDevisContainingIgnoreCaseOrObservationsContainingIgnoreCase(
            String entreprise,
            String objet,
            String cat,
            String devis,
            String observations
    );
}