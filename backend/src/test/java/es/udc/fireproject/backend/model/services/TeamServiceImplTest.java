package es.udc.fireproject.backend.model.services;

import es.udc.fireproject.backend.model.entities.team.Team;
import es.udc.fireproject.backend.model.entities.team.TeamRepository;
import es.udc.fireproject.backend.model.exceptions.InstanceNotFoundException;
import es.udc.fireproject.backend.model.services.organization.OrganizationService;
import es.udc.fireproject.backend.model.services.team.TeamServiceImpl;
import es.udc.fireproject.backend.utils.OrganizationOM;
import es.udc.fireproject.backend.utils.TeamOM;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import java.util.List;

@SpringBootTest
@Transactional
class TeamServiceImplTest {

    @Mock
    OrganizationService organizationService;

    @Mock
    TeamRepository teamRepository;

    @InjectMocks
    TeamServiceImpl teamService;

    @Test
    void givenNoData_whenCallFindByCode_thenReturnEmptyList() {
        final List<Team> result = teamService.findByCode(null);

        Assertions.assertTrue(result.isEmpty(), "Result must be Empty");
    }

    @Test
    void givenValidData_whenCallFindByCode_thenReturnFoundedTeam() {

        List<Team> resultList = List.of(TeamOM.withDefaultValues());

        Mockito.when(teamRepository.findByCode(Mockito.anyString())).thenReturn(resultList);

        final List<Team> result = teamService.findByCode("");

        Assertions.assertEquals(resultList, result, "Result must contain the same elements");
    }


    @Test
    void givenValidData_whenCallCreate_thenReturnCreatedTeam() throws InstanceNotFoundException {
        Mockito.when(organizationService.findById(Mockito.any())).thenReturn(java.util.Optional.of(OrganizationOM.withDefaultValues()));

        Mockito.when(teamRepository.save(Mockito.any())).thenReturn(TeamOM.withDefaultValues());


        Assertions.assertEquals(TeamOM.withDefaultValues(), teamService.create("a", 1L), "Elements are not equal");
    }

    @Test
    void givenInvalidData_whenCallCreate_thenReturnConstraintViolationException() {
        Mockito.when(organizationService.findById(Mockito.any())).thenReturn(java.util.Optional.of(OrganizationOM.withDefaultValues()));

        Assertions.assertThrows(ConstraintViolationException.class, () ->
                        teamService.create("", 1L)
                , "ConstraintViolationException error was expected");
    }

    @Test
    void givenInvalidOrganizationId_whenCallCreate_thenReturnInstanceNotFoundException() {

        Assertions.assertThrows(InstanceNotFoundException.class, () ->
                        teamService.create("", 1L)
                , "InstanceNotFoundException error was expected");
    }


    @Test
    void givenValidId_whenDelete_thenDeletedSuccessfully() {
        Team team = TeamOM.withDefaultValues();
        teamService.deleteById(1L);

        Assertions.assertTrue(teamService.findByCode(team.getCode()).isEmpty(), "Expected result must be Empty");
    }


    @Test
    void givenInvalidCode_whenUpdate_thenConstraintViolationException() {
        Team team = TeamOM.withDefaultValues();
        team.setCode("");

        Mockito.when(teamRepository.findById(Mockito.any())).thenReturn(java.util.Optional.of(TeamOM.withDefaultValues()));

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
        Team team = TeamOM.withDefaultValues();
        team.setCode("New Name");

        Mockito.when(teamRepository.findById(Mockito.any())).thenReturn(java.util.Optional.of(TeamOM.withDefaultValues()));
        Mockito.when(teamRepository.save(Mockito.any())).thenReturn(team);

        Team updatedTeam = teamService.update(team.getId(), team.getCode());
        Assertions.assertEquals(team, updatedTeam);
    }

}
