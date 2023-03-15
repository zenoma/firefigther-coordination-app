package es.udc.fireproject.backend.integration.services;

import es.udc.fireproject.backend.model.entities.organization.Organization;
import es.udc.fireproject.backend.model.entities.organization.OrganizationType;
import es.udc.fireproject.backend.model.entities.team.Team;
import es.udc.fireproject.backend.model.entities.user.User;
import es.udc.fireproject.backend.model.exceptions.DuplicateInstanceException;
import es.udc.fireproject.backend.model.exceptions.InstanceNotFoundException;
import es.udc.fireproject.backend.model.exceptions.TeamAlreadyDismantledException;
import es.udc.fireproject.backend.model.exceptions.TeamAlreadyExistException;
import es.udc.fireproject.backend.model.services.personalmanagement.PersonalManagementService;
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
    PersonalManagementService personalManagementService;

    @Test
    void givenNoData_whenCallFindByCode_thenReturnEmptyList() {
        final List<Team> result = personalManagementService.findTeamByCode("");

        Assertions.assertTrue(result.isEmpty(), "Result must be Empty");
    }

    @Test
    void givenValidData_whenCallFindByCode_thenReturnFoundTeam() throws InstanceNotFoundException, TeamAlreadyExistException {
        OrganizationType organizationType = personalManagementService.createOrganizationType(OrganizationTypeOM.withDefaultValues().getName());
        Organization organization = OrganizationOM.withDefaultValues();
        organization.setOrganizationType(organizationType);
        organization = personalManagementService.createOrganization(organization);

        Team team = TeamOM.withDefaultValues();
        team = personalManagementService.createTeam(team.getCode(),
                organization.getId());
        List<Team> resultList = List.of(team);


        final List<Team> result = personalManagementService.findTeamByCode(team.getCode());

        Assertions.assertEquals(resultList, result, "Result must contain the same elements");
    }


    @Test
    void givenInvalidData_whenCallCreate_thenReturnConstraintViolationException() {
        OrganizationType organizationType = personalManagementService.createOrganizationType(OrganizationTypeOM.withDefaultValues().getName());
        Organization organization = OrganizationOM.withDefaultValues();
        organization.setOrganizationType(organizationType);
        organization = personalManagementService.createOrganization(organization);

        Long id = organization.getId();
        Assertions.assertThrows(ConstraintViolationException.class, () ->
                        personalManagementService.createTeam("", id)
                , "ConstraintViolationException error was expected");
    }

    @Test
    void givenInvalidOrganizationId_whenCallCreate_thenReturnInstanceNotFoundException() {

        Assertions.assertThrows(InstanceNotFoundException.class, () ->
                        personalManagementService.createTeam("", 1L)
                , "InstanceNotFoundException error was expected");
    }


    @Test
    void givenValidId_whenDelete_thenDeletedSuccessfully() throws InstanceNotFoundException, TeamAlreadyExistException, TeamAlreadyDismantledException {
        OrganizationType organizationType = personalManagementService.createOrganizationType(OrganizationTypeOM.withDefaultValues().getName());
        Organization organization = OrganizationOM.withDefaultValues();
        organization.setOrganizationType(organizationType);
        organization = personalManagementService.createOrganization(organization);

        Team team = TeamOM.withDefaultValues();
        team = personalManagementService.createTeam(team.getCode(),
                organization.getId());
        personalManagementService.dismantleTeamById(team.getId());

        Assertions.assertTrue(personalManagementService.findTeamByCode(team.getCode()).isEmpty(), "Expected result must be Empty");
    }


    @Test
    void givenInvalidCode_whenUpdate_thenConstraintViolationException() throws InstanceNotFoundException, TeamAlreadyExistException {
        Team team = TeamOM.withDefaultValues();
        Organization organization = team.getOrganization();
        OrganizationType organizationType = organization.getOrganizationType();
        personalManagementService.createOrganizationType(organizationType.getName());
        organization = personalManagementService.createOrganization(organization);


        team = personalManagementService.createTeam(team.getCode(), organization.getId());
        team.setCode("");


        Long id = team.getId();
        String code = team.getCode();
        Assertions.assertThrows(ConstraintViolationException.class, () -> personalManagementService.updateTeam(id, code),
                "ConstraintViolationException error was expected");
    }

    @Test
    void givenInvalidId_whenUpdate_thenInstanceNotFoundException() {

        Assertions.assertThrows(InstanceNotFoundException.class, () -> personalManagementService.updateTeam(-1L, ""),
                "InstanceNotFoundException error was expected");
    }

    @Test
    void givenValidCode_whenUpdate_thenUpdateSuccessfully() throws InstanceNotFoundException, TeamAlreadyExistException {
        OrganizationType organizationType = personalManagementService.createOrganizationType(OrganizationTypeOM.withDefaultValues().getName());
        Organization organization = OrganizationOM.withDefaultValues();
        organization.setOrganizationType(organizationType);
        organization = personalManagementService.createOrganization(organization);

        Team team = TeamOM.withDefaultValues();
        team = personalManagementService.createTeam(team.getCode(),
                organization.getId());
        team.setCode("New Name");


        Team updatedTeam = personalManagementService.updateTeam(team.getId(), team.getCode());
        Assertions.assertEquals(team, updatedTeam);
    }

    @Test
    void givenValidUser_whenAddMember_thenMemberAddedSuccessfully() throws InstanceNotFoundException, DuplicateInstanceException {

        User user = UserOM.withDefaultValues();
        Team team = TeamOM.withDefaultValues();
        user.setTeam(team);
        personalManagementService.signUp(user);
        team = personalManagementService.addMember(user.getTeam().getId(), user.getId());


        Assertions.assertTrue(personalManagementService.findAllUsersByTeamId(team.getId()).contains(user), "User must belong to the Team");
    }

    @Test
    void givenInvalidUser_whenAddMember_thenConstraintViolationException() throws InstanceNotFoundException, DuplicateInstanceException {

        User user = UserOM.withDefaultValues();
        Team team = TeamOM.withDefaultValues();
        user.setTeam(team);
        personalManagementService.signUp(user);


        final Team finalTeam = user.getTeam();
        Assertions.assertThrows(InstanceNotFoundException.class, () ->
                        personalManagementService.addMember(finalTeam.getId(), INVALID_USER_ID),
                "InstanceNotFoundException error was expected");
        Assertions.assertThrows(InstanceNotFoundException.class, () ->
                        personalManagementService.addMember(INVALID_TEAM_ID, user.getId()),
                "InstanceNotFoundException error was expected");

    }


    @Test
    void givenValidUser_whenDeleteMember_thenMemberDeletedSuccessfully() throws InstanceNotFoundException, DuplicateInstanceException {


        User user = UserOM.withDefaultValues();
        Team team = TeamOM.withDefaultValues();
        user.setTeam(team);
        personalManagementService.signUp(user);
        personalManagementService.addMember(user.getTeam().getId(), user.getId());

        Assertions.assertTrue(personalManagementService.findAllUsersByTeamId(user.getTeam().getId()).contains(user), "User must belong to the Team");

        personalManagementService.deleteMember(user.getTeam().getId(), user.getId());

        Assertions.assertNull(user.getTeam(), "User must not belong to the Team");
    }

    @Test
    void givenInvalidUser_whenDeleteMember_thenConstraintViolationException() throws InstanceNotFoundException, DuplicateInstanceException {


        User user = UserOM.withDefaultValues();
        Team team = TeamOM.withDefaultValues();
        user.setTeam(team);
        personalManagementService.signUp(user);

        personalManagementService.addMember(user.getTeam().getId(), user.getId());

        Assertions.assertTrue(personalManagementService.findAllUsersByTeamId(user.getTeam().getId()).contains(user), "User must belong to the Team");


        final Team finalTeam = user.getTeam();
        Assertions.assertThrows(InstanceNotFoundException.class, () ->
                        personalManagementService.deleteMember(finalTeam.getId(), INVALID_USER_ID),
                "InstanceNotFoundException error was expected");
        Assertions.assertThrows(InstanceNotFoundException.class, () ->
                        personalManagementService.deleteMember(INVALID_TEAM_ID, user.getId()),
                "InstanceNotFoundException error was expected");

    }

    @Test
    void givenValidUsers_whenFindAllUsers_thenNumberFoundCorrect() throws InstanceNotFoundException, DuplicateInstanceException {
        Team team = TeamOM.withDefaultValues();
        int itemNumber = 3;
        List<User> userList = UserOM.withRandomNames(itemNumber);
        for (User user : userList) {
            user.setTeam(team);
            personalManagementService.signUp(user);
            personalManagementService.addMember(user.getTeam().getId(), user.getId());
        }
        team = userList.get(0).getTeam();

        Assertions.assertEquals(personalManagementService.findAllUsersByTeamId(team.getId()).size(), itemNumber, "Size must be equal to added Members");
    }

    @Test
    void givenTeamInvalidID_whenFindAllUsers_thenConstraintViolationException() throws InstanceNotFoundException, DuplicateInstanceException {

        Team team = TeamOM.withDefaultValues();

        int itemNumber = 3;
        List<User> userList = UserOM.withRandomNames(itemNumber);
        for (User user : userList) {
            user.setTeam(team);
            personalManagementService.signUp(user);
            personalManagementService.addMember(user.getTeam().getId(), user.getId());
        }

        Assertions.assertThrows(InstanceNotFoundException.class, () -> personalManagementService.findAllUsersByTeamId(INVALID_TEAM_ID),
                "InstanceNotFoundException error was expected");

    }


}
