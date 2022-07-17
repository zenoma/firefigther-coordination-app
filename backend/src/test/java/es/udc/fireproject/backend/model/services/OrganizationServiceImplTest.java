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
import org.junit.jupiter.api.BeforeEach;
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
    private final Organization defaultOrganization = OrganizationOM.withDefaultValues();


    @Mock
    OrganizationTypeRepository organizationTypeRepository;

    @Mock
    OrganizationRepository organizationRepository;

    @InjectMocks
    OrganizationServiceImpl organizationService;


    @BeforeEach
    public void setUp() {
        defaultOrganization.setId(1L);

        Mockito.when(organizationTypeRepository.findByName(Mockito.anyString())).thenReturn(OrganizationTypeOM.withDefaultValues());
        Mockito.when(organizationRepository.save(Mockito.any())).thenReturn(defaultOrganization);

        Mockito.when(organizationRepository.findById(Mockito.any())).thenReturn(Optional.of(defaultOrganization));

    }


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

        final List<Organization> list = new ArrayList<>();
        list.add(defaultOrganization);

        Mockito.when(organizationRepository.findAll()).thenReturn(list);

        final List<Organization> result = organizationService.findAll();

        Assertions.assertFalse(result.isEmpty(), "Result must be not empty");
        Assertions.assertTrue(result.contains(list.get(0)), "Result must contain the same Data");
    }


    @Test
    void givenEmptyName_whenCreateOrganization_thenInvalidArgumentException() {
        defaultOrganization.setName("");

        Assertions.assertThrows(ConstraintViolationException.class, () ->
                        organizationService.create(defaultOrganization)
                , "ConstraintViolationException error was expected");
    }

    @Test
    void givenEmptyAddress_whenCreateOrganization_thenInvalidArgumentException() {
        defaultOrganization.setHeadquartersAddress("");

        Assertions.assertThrows(ConstraintViolationException.class, () ->
                organizationService.create(defaultOrganization), "ConstraintViolationException error was expected");
    }


    @Test
    void givenEmptyOrganizationTypeName_whenCreateOrganization_thenConstraintViolationException() {
        defaultOrganization.setName("");
        Assertions.assertThrows(ConstraintViolationException.class, () ->
                organizationService.create(defaultOrganization), "ConstraintViolationException error was expected");
    }


    @Test
    void givenValidData_whenCreateOrganization_thenReturnOrganizationWithId() {


        final Organization result = organizationService.create(defaultOrganization.getCode(),
                defaultOrganization.getName(),
                defaultOrganization.getHeadquartersAddress(),
                defaultOrganization.getLocation(),
                defaultOrganization.getOrganizationType().getName());

        Assertions.assertNotNull(result.getId(), "Id must be not Null");


    }

    @Test
    void givenValidName_whenFindByNameOrCode_thenFoundOrganization() {
        final List<Organization> organizationList = new ArrayList<>();
        organizationList.add(defaultOrganization);

        Mockito.when(organizationRepository.findByNameIgnoreCaseContainsOrCodeIgnoreCaseContains(Mockito.anyString(), Mockito.anyString())).thenReturn(organizationList);


        Assertions.assertEquals(organizationList, organizationService.findByNameOrCode(defaultOrganization.getName()));

    }

    @Test
    void givenValidCode_whenFindByNameOrCode_thenFoundOrganization() {
        final List<Organization> organizationList = new ArrayList<>();
        organizationList.add(defaultOrganization);

        Mockito.when(organizationRepository.findByNameIgnoreCaseContainsOrCodeIgnoreCaseContains(Mockito.anyString(), Mockito.anyString())).thenReturn(organizationList);


        Assertions.assertEquals(organizationList, organizationService.findByNameOrCode(""));

    }

    @Test
    void givenValidNameAndValidCode_whenFindByNameOrCode_thenFoundOrganization() {
        final List<Organization> organizationList = new ArrayList<>();
        organizationList.add(defaultOrganization);

        Mockito.when(organizationRepository.findByNameIgnoreCaseContainsOrCodeIgnoreCaseContains(Mockito.anyString(), Mockito.anyString())).thenReturn(organizationList);


        Assertions.assertEquals(organizationList, organizationService.findByNameOrCode(defaultOrganization.getName()));
    }

    @Test
    void givenInvalidName_whenFindByNameOrCode_thenFoundOrganization() {

        Mockito.when(organizationRepository.findByNameIgnoreCaseContainsOrCodeIgnoreCaseContains("", "")).thenReturn(null);


        Assertions.assertTrue(organizationService.findByNameOrCode(null).isEmpty(), "Found item must be null");
    }


    @Test
    void givenValidName_whenFindByNameOrCode_thenFoundMultipleOrganizations() {
        List<String> names = Arrays.asList("Centro 1", "Centro 2", "Centro 3");
        List<Organization> list = OrganizationOM.withNames(names);

        Mockito.when(organizationRepository.save(Mockito.any())).thenReturn(OrganizationOM.withDefaultValues());
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


        Mockito.when(organizationTypeRepository.findByName(Mockito.anyString())).thenReturn(organizationType);
        Organization createdOrganization = organizationService.create(defaultOrganization);

        Organization updatedOrganization = organizationService.update(createdOrganization.getId(),
                "New Name",
                "New Code",
                "New HeadQuarters Address",
                defaultOrganization.getLocation());

        Assertions.assertEquals(createdOrganization, updatedOrganization);

    }

    @Test
    void givenInvadalidData_whenUpdate_thenConstraintViolationException() {
        final OrganizationType organizationType = OrganizationTypeOM.withDefaultValues();
        Mockito.when(organizationTypeRepository.save(Mockito.any())).thenReturn(organizationType);
        organizationService.createOrganizationType(organizationType.getName());


        Mockito.when(organizationTypeRepository.findByName(Mockito.anyString())).thenReturn(organizationType);
        Organization createdOrganization = organizationService.create(defaultOrganization);

        Mockito.when(organizationRepository.findById(Mockito.isNull())).thenReturn(Optional.of(OrganizationOM.withDefaultValues()));

        Long id = createdOrganization.getId();
        Point location = createdOrganization.getLocation();
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


        Mockito.when(organizationTypeRepository.findByName(Mockito.anyString())).thenReturn(organizationType);
        Organization createdOrganization = organizationService.create(defaultOrganization);

        Point location = createdOrganization.getLocation();

        Mockito.when(organizationRepository.findById(-1L)).thenReturn(Optional.empty());
        Assertions.assertThrows(InstanceNotFoundException.class, () -> organizationService.update(-1L,
                        "",
                        "",
                        "",
                        location)
                , "InstanceNotFoundException error was expected");

    }

}