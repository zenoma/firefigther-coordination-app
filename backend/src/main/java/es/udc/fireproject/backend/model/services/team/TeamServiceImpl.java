package es.udc.fireproject.backend.model.services.team;

import es.udc.fireproject.backend.model.entities.organization.Organization;
import es.udc.fireproject.backend.model.entities.team.Team;
import es.udc.fireproject.backend.model.entities.team.TeamRepository;
import es.udc.fireproject.backend.model.entities.user.User;
import es.udc.fireproject.backend.model.entities.user.UserRepository;
import es.udc.fireproject.backend.model.exceptions.InstanceNotFoundException;
import es.udc.fireproject.backend.model.services.organization.OrganizationService;
import es.udc.fireproject.backend.model.services.utils.ConstraintValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TeamServiceImpl implements TeamService {

    private static final String USER_NOT_FOUND = "User not found";
    private static final String TEAM_NOT_FOUND = "Team not found";

    @Autowired
    OrganizationService organizationService;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public List<Team> findByCode(String code) {
        return teamRepository.findByCodeContains(code);
    }

    @Override
    public Team create(String code, Long organizationId) throws InstanceNotFoundException {

        Organization organization = organizationService.findById(organizationId);

        Team team = new Team(code, organization);
        team.setCreatedAt(LocalDateTime.now());

        ConstraintValidator.validate(team);

        return teamRepository.save(team);
    }

    @Override
    @Transactional
    public void deleteById(Long id) throws InstanceNotFoundException {
        if (findAllUsers(id) != null) {
            List<User> userList = new ArrayList<>(findAllUsers(id));
            for (User user : userList) {
                user.setTeam(null);
                userRepository.save(user);
            }
        }
        teamRepository.deleteById(id);
    }

    @Override
    public Team update(Long id, String code) throws InstanceNotFoundException {
        Team team = teamRepository.findById(id).orElseThrow(() -> new InstanceNotFoundException(TEAM_NOT_FOUND, id));

        team.setCode(code);

        ConstraintValidator.validate(team);
        return teamRepository.save(team);


    }

    @Override
    public Team addMember(Long teamId, Long userId) throws InstanceNotFoundException {
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new InstanceNotFoundException(TEAM_NOT_FOUND, teamId));

        User user = userRepository.findById(userId).orElseThrow(() -> new InstanceNotFoundException(USER_NOT_FOUND, userId));

        user.setTeam(team);
        userRepository.save(user);

        List<User> userList = team.getUserList();
        if (userList == null) {
            userList = new ArrayList<>();
        }
        if (!userList.contains(user)) {
            userList.add(user);
            team.setUserList(userList);
        }
        return teamRepository.save(team);
    }

    @Override
    public void deleteMember(Long teamId, Long userId) throws InstanceNotFoundException {
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new InstanceNotFoundException(TEAM_NOT_FOUND, teamId));
        User user = userRepository.findById(userId).orElseThrow(() -> new InstanceNotFoundException(USER_NOT_FOUND, userId));
        if (!team.getUserList().contains(user)) {
            throw new InstanceNotFoundException(USER_NOT_FOUND, userId);
        }

        user.setTeam(null);
        userRepository.save(user);

    }

    @Override
    public List<User> findAllUsers(Long id) throws InstanceNotFoundException {
        return teamRepository.findById(id).orElseThrow(() -> new InstanceNotFoundException(TEAM_NOT_FOUND, id)).
                getUserList();
    }


    @Override
    public Team findById(Long teamId) throws InstanceNotFoundException {
        return teamRepository.findById(teamId).orElseThrow(() -> new InstanceNotFoundException(TEAM_NOT_FOUND, teamId));
    }
}
