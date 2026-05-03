package com.example.PFE.repository;


import com.example.PFE.model.Utilisateur;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface UtilisateurRepository extends MongoRepository<Utilisateur, String> {
    Optional<Utilisateur> findByEmail(String email);
    List<Utilisateur> findByRole(String role);
}
