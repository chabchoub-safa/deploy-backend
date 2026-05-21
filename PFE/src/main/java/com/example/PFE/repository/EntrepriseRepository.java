package com.example.PFE.repository;

import com.example.PFE.model.Entreprise;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface EntrepriseRepository extends MongoRepository<Entreprise, String> {
    List<Entreprise> findByNomEntrepriseContainingIgnoreCaseOrSpecialiteContainingIgnoreCase(
            String nomEntreprise,
            String specialite
    );
}