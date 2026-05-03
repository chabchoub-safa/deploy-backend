package com.example.PFE.repository;

import com.example.PFE.model.Machine;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MachineRepo extends MongoRepository<Machine,String> {
    List<Machine> findByDeletedFalseOrderByCreatedAtDesc();
    List<Machine> findByDeletedFalseAndCodeContainingIgnoreCase(String q);
    List<Machine> findByDeletedTrueOrderByCreatedAtDesc();

}