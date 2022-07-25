package es.udc.fireproject.backend.model.services.team;

import es.udc.fireproject.backend.model.entities.team.Team;
import es.udc.fireproject.backend.model.entities.user.User;
import es.udc.fireproject.backend.model.exceptions.InstanceNotFoundException;

import java.util.List;

public interface TeamService {

    List<Team> findByCode(String code);

    Team create(String code, Long organizationId) throws InstanceNotFoundException;

    void deleteById(Long id) throws InstanceNotFoundException;

    Team update(Long id, String code) throws InstanceNotFoundException;

    Team addMember(Long teamId, Long userId) throws InstanceNotFoundException;

    void deleteMember(Long teamId, Long userId) throws InstanceNotFoundException;

    List<User> findAllUsers(Long id) throws InstanceNotFoundException;

    Team findById(Long teamId) throws InstanceNotFoundException;

    Team findByUserId(Long userId) throws InstanceNotFoundException;

    List<Team> findByOrganizationId(Long organizationId);
}
