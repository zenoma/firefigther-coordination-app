package es.udc.fireproject.backend.integration.services;

import es.udc.fireproject.backend.model.entities.organization.Organization;
import es.udc.fireproject.backend.model.entities.organization.OrganizationType;
import es.udc.fireproject.backend.model.entities.team.Team;
import es.udc.fireproject.backend.model.entities.team.TeamRepository;
import es.udc.fireproject.backend.model.entities.user.User;
import es.udc.fireproject.backend.model.exceptions.DuplicateInstanceException;
import es.udc.fireproject.backend.model.exceptions.InstanceNotFoundException;
import es.udc.fireproject.backend.model.services.organization.OrganizationService;
import es.udc.fireproject.backend.model.services.team.TeamServiceImpl;
import es.udc.fireproject.backend.model.services.user.UserServiceImpl;
import es.udc.fireproject.backend.utils.OrganizationOM;
import es.udc.fireproject.backend.utils.OrganizationTypeOM;
import es.udc.fireproject.backend.utils.TeamOM;
import es.udc.fireproject.backend.utils.UserOM;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import java.util.List;

@SpringBootTest
@Transactional
class TeamServiceImplTest {

    private final Long INVALID_TEAM_ID = -1L;
    private final Long INVALID_USER_ID = -1L;

    @Autowired
    OrganizationService organizationService;
    @Autowired
    TeamRepository teamRepository;
    @Autowired
    TeamServiceImpl teamService;
    @Autowired
    UserServiceImpl userService;

    @Test
    void givenNoData_whenCallFindByCode_thenReturnEmptyList() {
        final List<Team> result = teamService.findByCode(null);

        Assertions.assertTrue(result.isEmpty(), "Result must be Empty");
    }

    @Test
    void givenValidData_whenCallFindByCode_thenReturnFoundedTeam() throws InstanceNotFoundException {
        OrganizationType organizationType = organizationService.createOrganizationType(OrganizationTypeOM.withDefaultValues().getName());
        Organization organization = OrganizationOM.withDefaultValues();
        organization.setOrganizationType(organizationType);
        organization = organizationService.create(organization);

        Team team = TeamOM.withDefaultValues();
        team = teamService.create(team.getCode(),
                organization.getId());
        List<Team> resultList = List.of(team);


        final List<Team> result = teamService.findByCode(team.getCode());

        Assertions.assertEquals(resultList, result, "Result must contain the same elements");
    }


    @Test
    void givenInvalidData_whenCallCreate_thenReturnConstraintViolationException() {
        OrganizationType organizationType = organizationService.createOrganizationType(OrganizationTypeOM.withDefaultValues().getName());
        Organization organization = OrganizationOM.withDefaultValues();
        organization.setOrganizationType(organizationType);
        organization = organizationService.create(organization);

        Long id = organization.getId();
        Assertions.assertThrows(ConstraintViolationException.class, () ->
                        teamService.create("", id)
                , "ConstraintViolationException error was expected");
    }

    @Test
    void givenInvalidOrganizationId_whenCallCreate_thenReturnInstanceNotFoundException() {

        Assertions.assertThrows(InstanceNotFoundException.class, () ->
                        teamService.create("", 1L)
                , "InstanceNotFoundException error was expected");
    }


    @Test
    void givenValidId_whenDelete_thenDeletedSuccessfully() throws InstanceNotFoundException {
        OrganizationType organizationType = organizationService.createOrganizationType(OrganizationTypeOM.withDefaultValues().getName());
        Organization organization = OrganizationOM.withDefaultValues();
        organization.setOrganizationType(organizationType);
        organization = organizationService.create(organization);

        Team team = TeamOM.withDefaultValues();
        team = teamService.create(team.getCode(),
                organization.getId());
        teamService.deleteById(team.getId());

        Assertions.assertTrue(teamService.findByCode(team.getCode()).isEmpty(), "Expected result must be Empty");
    }


    @Test
    void givenInvalidCode_whenUpdate_thenConstraintViolationException() throws InstanceNotFoundException {
        OrganizationType organizationType = organizationService.createOrganizationType(OrganizationTypeOM.withDefaultValues().getName());
        Organization organization = OrganizationOM.withDefaultValues();
        organization.setOrganizationType(organizationType);
        organization = organizationService.create(organization);

        Team team = TeamOM.withDefaultValues();
        team = teamService.create(team.getCode(),
                organization.getId());
        team.setCode("");


        Long id = team.getId();
        String code = team.getCode();
        Assertions.assertThrows(ConstraintViolationException.class, () -> teamService.update(id, code),
                "ConstraintViolationException error was expected");
    }

    @Test
    void givenInvalidId_whenUpdate_thenInstanceNotFoundException() {

        Assertions.assertThrows(InstanceNotFoundException.class, () -> teamService.update(-1L, ""),
                "InstanceNotFoundException error was expected");
    }

    @Test
    void givenValidCode_whenUpdate_thenUpdateSuccessfully() throws InstanceNotFoundException {
        OrganizationType organizationType = organizationService.createOrganizationType(OrganizationTypeOM.withDefaultValues().getName());
        Organization organization = OrganizationOM.withDefaultValues();
        organization.setOrganizationType(organizationType);
        organization = organizationService.create(organization);

        Team team = TeamOM.withDefaultValues();
        team = teamService.create(team.getCode(),
                organization.getId());
        team.setCode("New Name");


        Team updatedTeam = teamService.update(team.getId(), team.getCode());
        Assertions.assertEquals(team, updatedTeam);
    }

    @Test
    void givenValidUser_whenAddMember_thenMemberAddedSuccessfully() throws InstanceNotFoundException, DuplicateInstanceException {
        Organization organization = OrganizationOM.withDefaultValues();
        organizationService.createOrganizationType(OrganizationTypeOM.withDefaultValues().getName());
        organization = organizationService.create(organization);

        Team team = TeamOM.withDefaultValues();
        team = teamService.create(team.getCode(),
                organization.getId());


        User user = UserOM.withDefaultValues();
        userService.signUp(user);
        team = teamService.addMember(team.getId(), user.getId());


        Assertions.assertTrue(teamService.findAllUsers(team.getId()).contains(user), "User must belong to the Team");
    }

    @Test
    void givenInvalidUser_whenAddMember_thenConstraintViolationException() throws InstanceNotFoundException, DuplicateInstanceException {
        Organization organization = OrganizationOM.withDefaultValues();
        organizationService.createOrganizationType(OrganizationTypeOM.withDefaultValues().getName());
        organization = organizationService.create(organization);

        Team team = TeamOM.withDefaultValues();
        team = teamService.create(team.getCode(),
                organization.getId());


        User user = UserOM.withDefaultValues();
        userService.signUp(user);


        final Team finalTeam = team;
        Assertions.assertThrows(InstanceNotFoundException.class, () ->
                        teamService.addMember(finalTeam.getId(), INVALID_USER_ID),
                "InstanceNotFoundException error was expected");
        Assertions.assertThrows(InstanceNotFoundException.class, () ->
                        teamService.addMember(INVALID_TEAM_ID, user.getId()),
                "InstanceNotFoundException error was expected");

    }


    @Test
    void givenValidUser_whenDeleteMember_thenMemberAddedSuccessfully() throws InstanceNotFoundException, DuplicateInstanceException {
        Organization organization = OrganizationOM.withDefaultValues();
        organizationService.createOrganizationType(OrganizationTypeOM.withDefaultValues().getName());
        organization = organizationService.create(organization);

        Team team = TeamOM.withDefaultValues();
        team = teamService.create(team.getCode(),
                organization.getId());


        User user = UserOM.withDefaultValues();
        userService.signUp(user);
        teamService.addMember(team.getId(), user.getId());

        Assertions.assertTrue(teamService.findAllUsers(team.getId()).contains(user), "User must belong to the Team");

        teamService.deleteMember(team.getId(), user.getId());

        Assertions.assertFalse(teamService.findAllUsers(team.getId()).contains(user), "User must not belong to the Team");
    }

    @Test
    void givenInvalidUser_whenDeleteMember_thenConstraintViolationException() throws InstanceNotFoundException, DuplicateInstanceException {
        Organization organization = OrganizationOM.withDefaultValues();
        organizationService.createOrganizationType(OrganizationTypeOM.withDefaultValues().getName());
        organization = organizationService.create(organization);

        Team team = TeamOM.withDefaultValues();
        team = teamService.create(team.getCode(),
                organization.getId());


        User user = UserOM.withDefaultValues();
        userService.signUp(user);

        teamService.addMember(team.getId(), user.getId());

        Assertions.assertTrue(teamService.findAllUsers(team.getId()).contains(user), "User must belong to the Team");


        final Team finalTeam = team;
        Assertions.assertThrows(InstanceNotFoundException.class, () ->
                        teamService.deleteMember(finalTeam.getId(), INVALID_USER_ID),
                "InstanceNotFoundException error was expected");
        Assertions.assertThrows(InstanceNotFoundException.class, () ->
                        teamService.deleteMember(INVALID_TEAM_ID, user.getId()),
                "InstanceNotFoundException error was expected");

    }

    @Test
    void givenValidUsers_whenFindAllUsers_thenNumberFoundedCorrect() throws InstanceNotFoundException, DuplicateInstanceException {
        Organization organization = OrganizationOM.withDefaultValues();
        organizationService.createOrganizationType(OrganizationTypeOM.withDefaultValues().getName());
        organization = organizationService.create(organization);

        Team team = TeamOM.withDefaultValues();
        team = teamService.create(team.getCode(),
                organization.getId());


        int itemNumber = 3;
        List<User> userList = UserOM.withRandomNames(itemNumber);
        for (User user : userList) {
            userService.signUp(user);
            teamService.addMember(team.getId(), user.getId());
        }

        Assertions.assertEquals(teamService.findAllUsers(team.getId()).size(), itemNumber, "Size must be equal to added Members");
    }

    @Test
    void givenTeamInvalidID_whenFindAllUsers_thenConstraintViolationException() throws InstanceNotFoundException, DuplicateInstanceException {
        Organization organization = OrganizationOM.withDefaultValues();
        organizationService.createOrganizationType(OrganizationTypeOM.withDefaultValues().getName());
        organization = organizationService.create(organization);

        Team team = TeamOM.withDefaultValues();
        team = teamService.create(team.getCode(),
                organization.getId());


        int itemNumber = 3;
        List<User> userList = UserOM.withRandomNames(itemNumber);
        for (User user : userList) {
            userService.signUp(user);
            teamService.addMember(team.getId(), user.getId());
        }

        final Team finalTeam = team;
        Assertions.assertThrows(InstanceNotFoundException.class, () -> teamService.findAllUsers(INVALID_TEAM_ID),
                "InstanceNotFoundException error was expected");

    }

    @Test
    void givenValidUsers_whenFindUserById_thenUserFounded() throws InstanceNotFoundException, DuplicateInstanceException {
        Organization organization = OrganizationOM.withDefaultValues();
        organizationService.createOrganizationType(OrganizationTypeOM.withDefaultValues().getName());
        organization = organizationService.create(organization);

        Team team = TeamOM.withDefaultValues();
        team = teamService.create(team.getCode(),
                organization.getId());


        User user = UserOM.withDefaultValues();
        userService.signUp(user);
        teamService.addMember(team.getId(), user.getId());

        Assertions.assertEquals(teamService.findUserById(team.getId(), user.getId()), user, "User must be the same as the Excepted user");
    }

    @Test
    void givenInvalidData_whenFindUserById_thenUserFounded() throws InstanceNotFoundException, DuplicateInstanceException {
        Organization organization = OrganizationOM.withDefaultValues();
        organizationService.createOrganizationType(OrganizationTypeOM.withDefaultValues().getName());
        organization = organizationService.create(organization);

        Team team = TeamOM.withDefaultValues();
        team = teamService.create(team.getCode(),
                organization.getId());


        User user = UserOM.withDefaultValues();
        userService.signUp(user);
        teamService.addMember(team.getId(), user.getId());

        Assertions.assertThrows(InstanceNotFoundException.class, () -> teamService.findUserById(INVALID_TEAM_ID, user.getId()),
                "InstanceNotFoundException error was expected");
        final Team finalTeam = team;
        Assertions.assertThrows(InstanceNotFoundException.class, () -> teamService.findUserById(finalTeam.getId(), INVALID_USER_ID),
                "InstanceNotFoundException error was expected");
    }
}
