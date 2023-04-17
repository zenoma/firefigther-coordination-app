package es.udc.fireproject.backend.model.entities.logs;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TeamQuadrantLogRepository extends JpaRepository<TeamQuadrantLog, Long> {

    TeamQuadrantLog findByTeamIdAndQuadrantIdAndRetractAtIsNull(Long teamId, Integer quadrantId);

    List<TeamQuadrantLog> findByQuadrantIdAndDeployAtBetweenOrderByDeployAt(Integer quadrantId, LocalDateTime startDate, LocalDateTime endDate);


}
