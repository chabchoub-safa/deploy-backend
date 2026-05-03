package com.example.PFE.repository;

import com.example.PFE.model.Tissu;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TissuRepo extends MongoRepository<Tissu,String> {
    List<Tissu> findByCodeContainingIgnoreCase(String q);
    List<Tissu> findByClientId(String clientId);
    List<Tissu> findByLastTechnicienId(String lastTechnicienId);
    List<Tissu> findByClientEmail(String clientEmail);
}