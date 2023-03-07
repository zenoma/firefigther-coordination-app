package es.udc.fireproject.backend.model.entities.logs;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamQuadrantLogRepository extends JpaRepository<TeamQuadrantLog, Long> {

    TeamQuadrantLog findByTeamIdAndQuadrantIdAndRetractAtIsNull(Long teamId, Integer quadrantId);
}
