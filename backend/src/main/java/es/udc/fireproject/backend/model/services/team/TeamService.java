package es.udc.fireproject.backend.model.services.team;

import es.udc.fireproject.backend.model.entities.team.Team;
import es.udc.fireproject.backend.model.entities.user.User;
import es.udc.fireproject.backend.model.exceptions.InstanceNotFoundException;

import java.util.List;

public interface TeamService {

    List<Team> findByCode(String code);

    Team create(String code, Long organizationId) throws InstanceNotFoundException;

    void deleteById(Long id);

    Team update(Long id, String name) throws InstanceNotFoundException;

    Team addMember(Long teamId, Long userId) throws InstanceNotFoundException;

    void deleteMember(Long teamId, Long userId) throws InstanceNotFoundException;

    List<User> findAllUsers(Long id) throws InstanceNotFoundException;

    User findUserById(Long teamId, Long userId) throws InstanceNotFoundException;


}
