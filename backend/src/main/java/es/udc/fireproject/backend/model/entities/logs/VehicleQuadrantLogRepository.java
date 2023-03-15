package es.udc.fireproject.backend.model.entities.logs;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleQuadrantLogRepository extends JpaRepository<VehicleQuadrantLog, Long> {

    VehicleQuadrantLog findByVehicleIdAndQuadrantIdAndRetractAtIsNull(Long vehicleId, Integer quadrantId);
}
