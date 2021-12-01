package es.udc.fireproject.backend.model.services.team;

import es.udc.fireproject.backend.model.entities.team.Team;
import es.udc.fireproject.backend.model.exceptions.InstanceNotFoundException;

import java.util.List;

public interface TeamService {

    List<Team> findByCode(String code);

    Team create(String code, Long organizationId) throws InstanceNotFoundException;

    void deleteById(Long id);

    Team update(Team team);

}
