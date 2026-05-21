//package com.example.PFE.repository;
//
//import com.example.PFE.model.EnergyMeasurement;
//import org.springframework.data.mongodb.repository.MongoRepository;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//public interface EnergyMeasurementRepository extends MongoRepository<EnergyMeasurement, String> {
//
//    List<EnergyMeasurement> findByReceivedAtBetweenOrderByReceivedAtAsc(
//            LocalDateTime start,
//            LocalDateTime end
//    );
//    List<EnergyMeasurement> findByDeviceIdOrderByReceivedAtAsc(String deviceId);
//}
package com.example.PFE.repository;

import com.example.PFE.model.EnergyMeasurement;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface EnergyMeasurementRepository extends MongoRepository<EnergyMeasurement, String> {

    List<EnergyMeasurement> findByReceivedAtBetweenOrderByReceivedAtAsc(
            LocalDateTime start,
            LocalDateTime end
    );

    List<EnergyMeasurement> findByDeviceIdAndReceivedAtBetweenOrderByReceivedAtAsc(
            String deviceId,
            LocalDateTime start,
            LocalDateTime end
    );

    List<EnergyMeasurement> findByDeviceIdOrderByReceivedAtAsc(String deviceId);
}