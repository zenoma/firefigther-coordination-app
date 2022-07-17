package es.udc.fireproject.backend.model.entities.team;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Long> {

    List<Team> findByCodeContains(String code);

    List<Team> findByOrganizationId(Long organizationId);


}
