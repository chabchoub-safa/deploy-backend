package com.example.PFE.repository;

import com.example.PFE.model.Rappel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;

public interface RappelRepository extends MongoRepository<Rappel, String> {
    List<Rappel> findByDoneFalse();
    List<Rappel> findByDoneFalseAndProchaineDate(LocalDate date);
}