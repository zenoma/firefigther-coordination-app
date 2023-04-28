package es.udc.fireproject.backend.model.entities.logs;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface TeamQuadrantLogRepository extends JpaRepository<TeamQuadrantLog, Long> {

    TeamQuadrantLog findByTeamIdAndQuadrantIdAndRetractAtIsNull(Long teamId, Integer quadrantId);

    List<TeamQuadrantLog> findByQuadrantIdAndDeployAtBetweenOrderByDeployAt(Integer quadrantId, LocalDateTime startDate, LocalDateTime endDate);


    @Query("SELECT tql.team.id FROM TeamQuadrantLog tql WHERE tql.quadrant.id = :quadrantId")
    List<Long> findTeamsIdsByQuadrantsGid(@Param("quadrantId") Integer quadrantId);

}
