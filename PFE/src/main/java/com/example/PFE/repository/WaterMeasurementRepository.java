package com.example.PFE.repository;

import com.example.PFE.model.WaterMeasurement;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface WaterMeasurementRepository extends MongoRepository<WaterMeasurement, String> {

    List<WaterMeasurement> findByReceivedAtBetweenOrderByReceivedAtAsc(
            LocalDateTime start,
            LocalDateTime end
    );

    List<WaterMeasurement> findByReceivedAtGreaterThanEqualOrderByReceivedAtAsc(
            LocalDateTime start
    );
    List<WaterMeasurement> findByDeviceIdAndReceivedAtBetweenOrderByReceivedAtAsc(
            String deviceId,
            LocalDateTime start,
            LocalDateTime end
    );

    List<WaterMeasurement> findByDeviceIdOrderByReceivedAtAsc(String deviceId);

}
