package es.udc.fireproject.backend.model.services.team;

import es.udc.fireproject.backend.model.entities.organization.Organization;
import es.udc.fireproject.backend.model.entities.team.Team;
import es.udc.fireproject.backend.model.entities.team.TeamRepository;
import es.udc.fireproject.backend.model.exceptions.InstanceNotFoundException;
import es.udc.fireproject.backend.model.services.organization.OrganizationService;
import es.udc.fireproject.backend.model.services.utils.ConstraintValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class TeamServiceImpl implements TeamService {

    @Autowired
    OrganizationService organizationService;

    @Autowired
    TeamRepository teamRepository;

    @Override
    public List<Team> findByCode(String code) {
        return teamRepository.findByCode(code);
    }

    @Override
    public Team create(String code, Long organizationId) throws InstanceNotFoundException {

        Organization organization;
        organization = organizationService.findById(organizationId).orElseThrow(() ->
                new InstanceNotFoundException("Organization not found", organizationId));

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
    public Team update(Team team) {

        ConstraintValidator.validate(team);
        return teamRepository.save(team);
    }
}
