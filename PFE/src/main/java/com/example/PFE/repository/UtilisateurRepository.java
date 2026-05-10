package com.example.PFE.repository;


import com.example.PFE.model.Utilisateur;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.util.List;
import java.util.Optional;

public interface UtilisateurRepository extends MongoRepository<Utilisateur, String> {
    Optional<Utilisateur> findByEmail(String email);
    List<Utilisateur> findByRole(String role);
    List<Utilisateur> findByRoleAndDeletedFalse(String role);
    List<Utilisateur> findByDeletedFalse();
    List<Utilisateur> findByRoleAndDeletedTrue(String role);
    @Query("{ '$or': [ { 'deleted': false }, { 'deleted': { '$exists': false } } ] }")
    List<Utilisateur> findActiveUsers();

    @Query("{ 'role': ?0, '$or': [ { 'deleted': false }, { 'deleted': { '$exists': false } } ] }")
    List<Utilisateur> findActiveUsersByRole(String role);

    @Query("{ 'role': ?0, 'deleted': true }")
    List<Utilisateur> findDeletedUsersByRole(String role);
}
