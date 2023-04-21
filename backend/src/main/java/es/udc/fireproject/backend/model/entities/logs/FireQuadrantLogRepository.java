package es.udc.fireproject.backend.model.entities.logs;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface FireQuadrantLogRepository extends JpaRepository<FireQuadrantLog, Long> {

    FireQuadrantLog findByFireIdAndQuadrantId(Long fireId, Integer quadrantId);

    List<FireQuadrantLog> findByFireIdAndLinkedAtBetweenOrderByLinkedAt(Long fireId, LocalDateTime startDate, LocalDateTime endDate);

}
