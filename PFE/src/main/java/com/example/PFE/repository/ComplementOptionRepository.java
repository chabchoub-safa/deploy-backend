package com.example.PFE.repository;

import com.example.PFE.model.ComplementOption;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ComplementOptionRepository extends MongoRepository<ComplementOption, String> {
    Optional<ComplementOption> findByNomIgnoreCase(String nom);
    List<ComplementOption> findByDeletedFalse();
    List<ComplementOption> findByDeletedTrue();
}