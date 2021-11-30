package es.udc.fireproject.backend.model.services.organization;

import es.udc.fireproject.backend.model.entities.organization.Organization;
import es.udc.fireproject.backend.model.entities.organization.OrganizationRepository;
import es.udc.fireproject.backend.model.entities.organization.OrganizationType;
import es.udc.fireproject.backend.model.entities.organization.OrganizationTypeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


class OrganizationTypeOM {
    static OrganizationType withDefaultValues() {
        return new OrganizationType("Dummy Type Name");
    }

    static OrganizationType withInvalidName() {
        OrganizationType organizationType = withDefaultValues();
        organizationType.setName(null);
        return organizationType;
    }
}

class OrganizationOM {
    static Organization withDefaultValues() {
        final GeometryFactory geoFactory = new GeometryFactory();

        return new Organization("ORG-01",
                "Centro de Coordinación Central",
                "Calle alguna", geoFactory.createPoint(new Coordinate(-45, 45)),
                OrganizationTypeOM.withDefaultValues());
    }

    static List<Organization> withNames(List<String> names) {
        final GeometryFactory geoFactory = new GeometryFactory();
        final List<Organization> result = new ArrayList<>();
        Organization organization;
        int count = 0;

        for (String name : names) {
            organization = new Organization(name.substring(0, 3).toUpperCase() + "-" + count,
                    name,
                    "Calle alguna", geoFactory.createPoint(new Coordinate(-45, 45)),
                    OrganizationTypeOM.withDefaultValues());
            result.add(organization);
            count++;
        }
        return result;

    }
}

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
                        organizationService.createOrganization(organization)
                , "ConstraintViolationException error was expected");
    }

    @Test
    void givenEmptyAddress_whenCreationOrganization_thenInvalidArgumentException() {
        final Organization organization = OrganizationOM.withDefaultValues();
        organization.setHeadquartersAddress("");

        Assertions.assertThrows(ConstraintViolationException.class, () ->
                organizationService.createOrganization(organization), "ConstraintViolationException error was expected");
    }

    @Test
    void givenInvalidLocation_whenCreationOrganization_thenInvalidArgumentException() {
        final Organization organization = OrganizationOM.withDefaultValues();
        organization.setLocation(null);

        Assertions.assertThrows(ConstraintViolationException.class, () ->
                organizationService.createOrganization(organization), "ConstraintViolationException error was expected");
    }

    @Test
    void givenEmptyOrganizationTypeName_whenCreationOrganization_thenInvalidArgumentException() {
        final Organization organization = OrganizationOM.withDefaultValues();

        Assertions.assertThrows(ConstraintViolationException.class, () ->
                organizationService.createOrganization(organization), "ConstraintViolationException error was expected");
    }


    @Test
    void givenValidData_whenCreationOrganization_thenReturnOrganizationWithId() {

        final Organization organization = OrganizationOM.withDefaultValues();

        organization.setId(1L);

        Mockito.when(organizationRepository.save(Mockito.any())).thenReturn(organization);
        Mockito.when(organizationTypeRepository.findByName(Mockito.anyString())).thenReturn(OrganizationTypeOM.withDefaultValues());

        final Organization result = organizationService.createOrganization(organization.getCode(),
                organization.getName(),
                organization.getHeadquartersAddress(),
                organization.getLocation(),
                organization.getOrganizationType().getName());

        Assertions.assertNotNull(result.getId(), "Id must be not Null");

        // Create At must be null because the Object never persist in the Database
        Assertions.assertNull(result.getCreatedAt(), "Created date must be Null");

    }

    @Test
    void givenValidName_whenFindByNameOrCode_thenFoundedOrganization() {
        final Organization organization = OrganizationOM.withDefaultValues();
        final List<Organization> organizationList = new ArrayList<>();
        organizationList.add(organization);

        Mockito.when(organizationRepository.save(Mockito.any())).thenReturn(organization);
        Mockito.when(organizationTypeRepository.findByName(Mockito.anyString())).thenReturn(OrganizationTypeOM.withDefaultValues());
        Mockito.when(organizationRepository.findByNameIgnoreCaseOrCode(Mockito.anyString(), Mockito.anyString())).thenReturn(organizationList);


        Assertions.assertEquals(organizationList, organizationService.findByNameOrCode(organization.getName(), ""));

    }

    @Test
    void givenValidCode_whenFindByNameOrCode_thenFoundedOrganization() {
        final Organization organization = OrganizationOM.withDefaultValues();
        final List<Organization> organizationList = new ArrayList<>();
        organizationList.add(organization);

        Mockito.when(organizationRepository.save(Mockito.any())).thenReturn(organization);
        Mockito.when(organizationTypeRepository.findByName(Mockito.anyString())).thenReturn(OrganizationTypeOM.withDefaultValues());
        Mockito.when(organizationRepository.findByNameIgnoreCaseOrCode(Mockito.anyString(), Mockito.anyString())).thenReturn(organizationList);


        Assertions.assertEquals(organizationList, organizationService.findByNameOrCode("", organization.getCode()));

    }

    @Test
    void givenValidNameAndValidCode_whenFindByNameOrCode_thenFoundedOrganization() {
        final Organization organization = OrganizationOM.withDefaultValues();
        final List<Organization> organizationList = new ArrayList<>();
        organizationList.add(organization);

        Mockito.when(organizationRepository.save(Mockito.any())).thenReturn(organization);
        Mockito.when(organizationTypeRepository.findByName(Mockito.anyString())).thenReturn(OrganizationTypeOM.withDefaultValues());
        Mockito.when(organizationRepository.findByNameIgnoreCaseOrCode(Mockito.anyString(), Mockito.anyString())).thenReturn(organizationList);


        Assertions.assertEquals(organizationList, organizationService.findByNameOrCode(organization.getName(), organization.getCode()));
    }

    @Test
    void givenInvalidName_whenFindByNameOrCode_thenFoundedOrganization() {
        final Organization organization = OrganizationOM.withDefaultValues();

        Mockito.when(organizationRepository.save(Mockito.any())).thenReturn(organization);
        Mockito.when(organizationTypeRepository.findByName(Mockito.anyString())).thenReturn(OrganizationTypeOM.withDefaultValues());
        Mockito.when(organizationRepository.findByNameIgnoreCaseOrCode("", "")).thenReturn(null);


        Assertions.assertTrue(organizationService.findByNameOrCode(null, null).isEmpty(), "The item founded must be null");
    }


    @Test
    void givenValidName_whenFindByNameOrCode_thenFoundedMultipleOrganizations() {
        List<String> names = Arrays.asList("Centro 1", "Centro 2", "Centro 3");
        List<Organization> list = OrganizationOM.withNames(names);

        Mockito.when(organizationRepository.save(Mockito.any())).thenReturn(OrganizationOM.withDefaultValues());
        Mockito.when(organizationTypeRepository.findByName(Mockito.anyString())).thenReturn(OrganizationTypeOM.withDefaultValues());
        Mockito.when(organizationRepository.findByNameIgnoreCaseOrCode(Mockito.anyString(), Mockito.anyString())).thenReturn(list);

        Assertions.assertEquals(3, organizationService.findByNameOrCode("Centro", "").size(),
                "Expected results must be 3");
    }


    @Test
    void givenValidName_whenFindByOrganizationTypeName_thenFoundedMultipleOrganizations() {
        List<String> names = Arrays.asList("Centro 1", "Centro 2", "Centro 3");
        List<Organization> list = OrganizationOM.withNames(names);

        Mockito.when(organizationRepository.findByOrganizationType_Name(Mockito.anyString())).thenReturn(list);

        Assertions.assertEquals(3, organizationService.findByOrganizationTypeName("Dummy").size(),
                "Expected results must be 3");
    }

}