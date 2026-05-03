package com.example.PFE.repository;

import com.example.PFE.model.ScanAction;
import com.example.PFE.model.ScanEvent;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ScanEventRepo extends MongoRepository<ScanEvent, String> {
    List<ScanEvent> findByTissuIdOrderByTsAsc(String tissuId);

    List<ScanEvent> findByTissuIdAndMachineIdOrderByTsAsc(String tissuId, String machineId);

    Optional<ScanEvent> findTopByTissuIdAndMachineIdAndActionAndClosedFalseOrderByTsDesc(
            String tissuId,
            String machineId,
            ScanAction action
    );

    List<ScanEvent> findByActionAndClosedFalse(ScanAction action);
}