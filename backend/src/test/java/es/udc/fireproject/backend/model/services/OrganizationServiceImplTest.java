package es.udc.fireproject.backend.model.services;

import es.udc.fireproject.backend.model.entities.organization.Organization;
import es.udc.fireproject.backend.model.entities.organization.OrganizationRepository;
import es.udc.fireproject.backend.model.entities.organization.OrganizationType;
import es.udc.fireproject.backend.model.entities.organization.OrganizationTypeRepository;
import es.udc.fireproject.backend.model.exceptions.InstanceNotFoundException;
import es.udc.fireproject.backend.model.services.organization.OrganizationServiceImpl;
import es.udc.fireproject.backend.utils.OrganizationOM;
import es.udc.fireproject.backend.utils.OrganizationTypeOM;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Point;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@SpringBootTest
@Transactional
class OrganizationServiceImplTest {
    @Mock
    OrganizationTypeRepository organizationTypeRepository;

    @Mock
    OrganizationRepository organizationRepository;

    @InjectMocks
    OrganizationServiceImpl organizationService;


    @Test
    void givenNoData_whenCallFindAllOrganizationTypes_thenReturnEmptyList() {
        final List<OrganizationType> result = organizationService.findAllOrganizationTypes();

        Assertions.assertTrue(result.isEmpty(), "Result must be Empty");
    }

    @Test
    void givenData_whenFindAllOrganizationTypes_thenReturnNotEmptyList() {
        final OrganizationType organizationType = OrganizationTypeOM.withDefaultValues();

        final List<OrganizationType> list = new ArrayList<>();
        list.add(organizationType);

        Mockito.when(organizationTypeRepository.findAll()).thenReturn(list);

        final List<OrganizationType> result = organizationService.findAllOrganizationTypes();

        Assertions.assertTrue(result.contains(list.get(0)), "Result must contain the same Data");
    }

    @Test
    void givenNoData_whenCallFindAll_thenReturnEmptyList() {
        final List<Organization> result = organizationService.findAll();

        Assertions.assertTrue(result.isEmpty(), "Result must be Empty");
    }

    @Test
    void givenData_whenFindAll_thenReturnNotEmptyList() {

        final Organization organization = OrganizationOM.withDefaultValues();

        final List<Organization> list = new ArrayList<>();
        list.add(organization);

        Mockito.when(organizationRepository.findAll()).thenReturn(list);

        final List<Organization> result = organizationService.findAll();

        Assertions.assertFalse(result.isEmpty(), "Result must be not empty");
        Assertions.assertTrue(result.contains(list.get(0)), "Result must contain the same Data");
    }


    @Test
    void givenEmptyName_whenCreationOrganization_thenInvalidArgumentException() {
        final Organization organization = OrganizationOM.withDefaultValues();
        organization.setName("");

        Assertions.assertThrows(ConstraintViolationException.class, () ->
                        organizationService.create(organization)
                , "ConstraintViolationException error was expected");
    }

    @Test
    void givenEmptyAddress_whenCreationOrganization_thenInvalidArgumentException() {
        final Organization organization = OrganizationOM.withDefaultValues();
        organization.setHeadquartersAddress("");

        Assertions.assertThrows(ConstraintViolationException.class, () ->
                organizationService.create(organization), "ConstraintViolationException error was expected");
    }

    @Test
    void givenInvalidLocation_whenCreationOrganization_thenInvalidArgumentException() {
        final Organization organization = OrganizationOM.withDefaultValues();
        organization.setLocation(null);

        Assertions.assertThrows(ConstraintViolationException.class, () ->
                organizationService.create(organization), "ConstraintViolationException error was expected");
    }

    @Test
    void givenEmptyOrganizationTypeName_whenCreationOrganization_thenInvalidArgumentException() {
        final Organization organization = OrganizationOM.withDefaultValues();

        Assertions.assertThrows(ConstraintViolationException.class, () ->
                organizationService.create(organization), "ConstraintViolationException error was expected");
    }


    @Test
    void givenValidData_whenCreationOrganization_thenReturnOrganizationWithId() {

        final Organization organization = OrganizationOM.withDefaultValues();

        organization.setId(1L);

        Mockito.when(organizationRepository.save(Mockito.any())).thenReturn(organization);
        Mockito.when(organizationTypeRepository.findByName(Mockito.anyString())).thenReturn(OrganizationTypeOM.withDefaultValues());

        final Organization result = organizationService.create(organization.getCode(),
                organization.getName(),
                organization.getHeadquartersAddress(),
                organization.getLocation(),
                organization.getOrganizationType().getName());

        Assertions.assertNotNull(result.getId(), "Id must be not Null");

        // Create At must be null because the Object never persist in the Database
        Assertions.assertNull(result.getCreatedAt(), "Created date must be Null");

    }

    @Test
    void givenValidName_whenFindByNameOrCode_thenFoundOrganization() {
        final Organization organization = OrganizationOM.withDefaultValues();
        final List<Organization> organizationList = new ArrayList<>();
        organizationList.add(organization);

        Mockito.when(organizationRepository.save(Mockito.any())).thenReturn(organization);
        Mockito.when(organizationTypeRepository.findByName(Mockito.anyString())).thenReturn(OrganizationTypeOM.withDefaultValues());
        Mockito.when(organizationRepository.findByNameIgnoreCaseContainsOrCodeIgnoreCaseContains(Mockito.anyString(), Mockito.anyString())).thenReturn(organizationList);


        Assertions.assertEquals(organizationList, organizationService.findByNameOrCode(organization.getName()));

    }

    @Test
    void givenValidCode_whenFindByNameOrCode_thenFoundOrganization() {
        final Organization organization = OrganizationOM.withDefaultValues();
        final List<Organization> organizationList = new ArrayList<>();
        organizationList.add(organization);

        Mockito.when(organizationRepository.save(Mockito.any())).thenReturn(organization);
        Mockito.when(organizationTypeRepository.findByName(Mockito.anyString())).thenReturn(OrganizationTypeOM.withDefaultValues());
        Mockito.when(organizationRepository.findByNameIgnoreCaseContainsOrCodeIgnoreCaseContains(Mockito.anyString(), Mockito.anyString())).thenReturn(organizationList);


        Assertions.assertEquals(organizationList, organizationService.findByNameOrCode(""));

    }

    @Test
    void givenValidNameAndValidCode_whenFindByNameOrCode_thenFoundOrganization() {
        final Organization organization = OrganizationOM.withDefaultValues();
        final List<Organization> organizationList = new ArrayList<>();
        organizationList.add(organization);

        Mockito.when(organizationRepository.save(Mockito.any())).thenReturn(organization);
        Mockito.when(organizationTypeRepository.findByName(Mockito.anyString())).thenReturn(OrganizationTypeOM.withDefaultValues());
        Mockito.when(organizationRepository.findByNameIgnoreCaseContainsOrCodeIgnoreCaseContains(Mockito.anyString(), Mockito.anyString())).thenReturn(organizationList);


        Assertions.assertEquals(organizationList, organizationService.findByNameOrCode(organization.getName()));
    }

    @Test
    void givenInvalidName_whenFindByNameOrCode_thenFoundOrganization() {
        final Organization organization = OrganizationOM.withDefaultValues();

        Mockito.when(organizationRepository.save(Mockito.any())).thenReturn(organization);
        Mockito.when(organizationTypeRepository.findByName(Mockito.anyString())).thenReturn(OrganizationTypeOM.withDefaultValues());
        Mockito.when(organizationRepository.findByNameIgnoreCaseContainsOrCodeIgnoreCaseContains("", "")).thenReturn(null);


        Assertions.assertTrue(organizationService.findByNameOrCode(null).isEmpty(), "Found item must be null");
    }


    @Test
    void givenValidName_whenFindByNameOrCode_thenFoundMultipleOrganizations() {
        List<String> names = Arrays.asList("Centro 1", "Centro 2", "Centro 3");
        List<Organization> list = OrganizationOM.withNames(names);

        Mockito.when(organizationRepository.save(Mockito.any())).thenReturn(OrganizationOM.withDefaultValues());
        Mockito.when(organizationTypeRepository.findByName(Mockito.anyString())).thenReturn(OrganizationTypeOM.withDefaultValues());
        Mockito.when(organizationRepository.findByNameIgnoreCaseContainsOrCodeIgnoreCaseContains(Mockito.anyString(), Mockito.anyString())).thenReturn(list);

        Assertions.assertEquals(3, organizationService.findByNameOrCode("Centro").size(),
                "Expected results must be 3");
    }


    @Test
    void givenValidName_whenFindByOrganizationTypeName_thenFoundMultipleOrganizations() {
        List<String> names = Arrays.asList("Centro 1", "Centro 2", "Centro 3");
        List<Organization> list = OrganizationOM.withNames(names);

        Mockito.when(organizationRepository.findByOrganizationType_NameIgnoreCaseContains(Mockito.anyString())).thenReturn(list);

        Assertions.assertEquals(3, organizationService.findByOrganizationTypeName("Dummy").size(),
                "Expected results must be 3");
    }

    @Test
    void givenValidId_whenDelete_thenDeletedSuccessfully() {
        Organization organization = OrganizationOM.withDefaultValues();

        Mockito.when(organizationRepository.findAll()).thenReturn(new ArrayList<>());
        organizationService.deleteById(organization.getId());

        Assertions.assertTrue(organizationService.findAll().isEmpty(), "Expected result must be Empty");
    }

    @Test
    void givenValidData_whenUpdate_thenUpdatedSuccessfully() throws InstanceNotFoundException {
        final OrganizationType organizationType = OrganizationTypeOM.withDefaultValues();
        Mockito.when(organizationTypeRepository.save(Mockito.any())).thenReturn(organizationType);
        organizationService.createOrganizationType(organizationType.getName());

        Organization organization = OrganizationOM.withDefaultValues();
        Mockito.when(organizationRepository.save(Mockito.any())).thenReturn(organization);
        Mockito.when(organizationTypeRepository.findByName(Mockito.anyString())).thenReturn(organizationType);
        organization = organizationService.create(organization);

        Mockito.when(organizationRepository.findById(Mockito.isNull())).thenReturn(Optional.of(OrganizationOM.withDefaultValues()));
        Organization updatedOrganization = organizationService.update(organization.getId(),
                "New Name",
                "New Code",
                "New HeadQuarters Address",
                organization.getLocation());

        Assertions.assertEquals(organization, updatedOrganization);

    }

    @Test
    void givenInvadalidData_whenUpdate_thenConstraintViolationException() {
        final OrganizationType organizationType = OrganizationTypeOM.withDefaultValues();
        Mockito.when(organizationTypeRepository.save(Mockito.any())).thenReturn(organizationType);
        organizationService.createOrganizationType(organizationType.getName());

        Organization organization = OrganizationOM.withDefaultValues();
        Mockito.when(organizationRepository.save(Mockito.any())).thenReturn(organization);
        Mockito.when(organizationTypeRepository.findByName(Mockito.anyString())).thenReturn(organizationType);
        organization = organizationService.create(organization);

        Mockito.when(organizationRepository.findById(Mockito.isNull())).thenReturn(Optional.of(OrganizationOM.withDefaultValues()));

        Long id = organization.getId();
        Point location = organization.getLocation();
        Assertions.assertThrows(ConstraintViolationException.class, () -> organizationService.update(id,
                        "",
                        "",
                        "",
                        location)
                , "ConstraintViolationException error was expected");


    }

    @Test
    void givenInvadalidId_whenUpdate_thenInstanceNotFoundException() {
        final OrganizationType organizationType = OrganizationTypeOM.withDefaultValues();
        Mockito.when(organizationTypeRepository.save(Mockito.any())).thenReturn(organizationType);
        organizationService.createOrganizationType(organizationType.getName());

        Organization organization = OrganizationOM.withDefaultValues();
        Mockito.when(organizationRepository.save(Mockito.any())).thenReturn(organization);
        Mockito.when(organizationTypeRepository.findByName(Mockito.anyString())).thenReturn(organizationType);
        organization = organizationService.create(organization);

        Point location = organization.getLocation();
        Assertions.assertThrows(InstanceNotFoundException.class, () -> organizationService.update(-1L,
                        "",
                        "",
                        "",
                        location)
                , "InstanceNotFoundException error was expected");

    }

}