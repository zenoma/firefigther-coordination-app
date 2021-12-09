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
import java.util.Optional;

@Service
@Transactional
public class TeamServiceImpl implements TeamService {

    private static final String USER_NOT_FOUNDED = "User not founded";
    private static final String TEAM_NOT_FOUNDED = "Team not founded";
    private static final String ORGANIZATION_NOT_FOUNDED = "Organization not founded";

    @Autowired
    OrganizationService organizationService;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public List<Team> findByCode(String code) {
        return teamRepository.findByCode(code);
    }

    @Override
    public Team create(String code, Long organizationId) throws InstanceNotFoundException {

        Organization organization;
        organization = organizationService.findById(organizationId).orElseThrow(() ->
                new InstanceNotFoundException(ORGANIZATION_NOT_FOUNDED, organizationId));

        Team team = new Team(code, organization);
        team.setCreatedAt(LocalDateTime.now());

        ConstraintValidator.validate(team);

        return teamRepository.save(team);
    }

    @Override
    public void deleteById(Long id) {

        teamRepository.deleteById(id);
    }

    @Override
    public Team update(Long id, String code) throws InstanceNotFoundException {
        Optional<Team> teamOpt = teamRepository.findById(id);

        if (teamOpt.isEmpty()) {
            throw new InstanceNotFoundException(TEAM_NOT_FOUNDED, id);
        } else {
            Team team = teamOpt.get();
            team.setCode(code);

            ConstraintValidator.validate(team);
            return teamRepository.save(team);
        }
    }

    @Override
    public Team addMember(Long teamId, Long userId) throws InstanceNotFoundException {
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new InstanceNotFoundException(TEAM_NOT_FOUNDED, teamId));

        User user = userRepository.findById(userId).orElseThrow(() -> new InstanceNotFoundException(USER_NOT_FOUNDED, userId));

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
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new InstanceNotFoundException(TEAM_NOT_FOUNDED, teamId));

        User user = userRepository.findById(userId).orElseThrow(() -> new InstanceNotFoundException(USER_NOT_FOUNDED, userId));

        user.setTeam(null);
        userRepository.save(user);

        List<User> userList = team.getUserList();
        userList.remove(user);
        team.setUserList(userList);
        teamRepository.save(team);
    }

    @Override
    public List<User> findAllUsers(Long id) throws InstanceNotFoundException {
        return teamRepository.findById(id).orElseThrow(() -> new InstanceNotFoundException(TEAM_NOT_FOUNDED, id)).
                getUserList();
    }

    @Override
    public User findUserById(Long teamId, Long userId) throws InstanceNotFoundException {
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new InstanceNotFoundException(TEAM_NOT_FOUNDED, teamId));
        User user = userRepository.findById(userId).orElseThrow(() -> new InstanceNotFoundException(USER_NOT_FOUNDED, userId));
        return team.getUserList().get(team.getUserList().indexOf(user));
    }
}
