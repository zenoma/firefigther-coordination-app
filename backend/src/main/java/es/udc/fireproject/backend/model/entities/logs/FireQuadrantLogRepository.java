package es.udc.fireproject.backend.model.entities.logs;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FireQuadrantLogRepository extends JpaRepository<FireQuadrantLog, Long> {

    FireQuadrantLog findByFireIdAndQuadrantId(Long fireId, Integer quadrantId);
}
