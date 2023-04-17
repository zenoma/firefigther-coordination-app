package es.udc.fireproject.backend.model.entities.logs;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface VehicleQuadrantLogRepository extends JpaRepository<VehicleQuadrantLog, Long> {

    VehicleQuadrantLog findByVehicleIdAndQuadrantIdAndRetractAtIsNull(Long vehicleId, Integer quadrantId);

    List<VehicleQuadrantLog> findByQuadrantIdAndDeployAtBetweenOrderByDeployAt(Integer quadrantId, LocalDateTime startDate, LocalDateTime endDate);


}
