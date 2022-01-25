package es.udc.fireproject.backend.integration.services;

import es.udc.fireproject.backend.model.entities.organization.Organization;
import es.udc.fireproject.backend.model.entities.organization.OrganizationType;
import es.udc.fireproject.backend.model.exceptions.InstanceNotFoundException;
import es.udc.fireproject.backend.model.services.organization.OrganizationService;
import es.udc.fireproject.backend.utils.OrganizationOM;
import es.udc.fireproject.backend.utils.OrganizationTypeOM;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@SpringBootTest
@Transactional
class OrganizationServiceImplTest {

    @Autowired
    OrganizationService organizationService;

    @Test
    void givenInvalidString_whenCreateOrganizationType_theConstraintViolationException() {

        String name = OrganizationTypeOM.withInvalidName().getName();
        Assertions.assertThrows(ConstraintViolationException.class,
                () -> organizationService.createOrganizationType(name),
                "ConstraintViolationException error was expected");
    }

    @Test
    void givenValidData_whenCreationOrganization_thenReturnOrganizationWithId() {

        OrganizationType organizationType = OrganizationTypeOM.withDefaultValues();
        organizationType = organizationService.createOrganizationType(organizationType.getName());

        Organization organization = OrganizationOM.withDefaultValues();

        Organization result = organizationService.create(organization.getCode(),
                organization.getName(),
                organization.getHeadquartersAddress(),
                organization.getLocation(),
                organizationType.getName());

        Assertions.assertNotNull(result.getId(), "Id must be not Null");
        Assertions.assertNotNull(result.getCreatedAt(), "Created date must be not Null");

    }

    @Test
    void givenValidOrganization_whenCreationOrganization_thenReturnOrganizationWithId() {

        OrganizationType organizationType = OrganizationTypeOM.withDefaultValues();
        organizationService.createOrganizationType(organizationType.getName());

        Organization organization = OrganizationOM.withDefaultValues();

        Organization result = organizationService.create(organization);

        Assertions.assertNotNull(result.getId(), "Id must be not Null");
        Assertions.assertNotNull(result.getCreatedAt(), "Created date must be not Null");

    }


    @Test
    void givenInvalidOrganizationData_whenCreationOrganization_thenConstraintViolationException() {

        Organization organization = OrganizationOM.withInvalidValues();

        Assertions.assertThrows(ConstraintViolationException.class,
                () -> organizationService.create(organization),
                "ConstraintViolationException error was expected");

    }

    @Test
    void givenValidName_whenFindByNameOrCode_thenFoundedOrganization() {
        final Organization organization = OrganizationOM.withDefaultValues();
        final List<Organization> organizationList = new ArrayList<>();
        organizationList.add(organization);

        organizationService.createOrganizationType(organization.getOrganizationType().getName());
        organizationService.create(organization);

        Assertions.assertEquals(organizationList, organizationService.findByNameOrCode(organization.getName()));
    }

    @Test
    void givenInvalidName_whenFindByNameOrCode_thenReturnEmptyList() {

        Assertions.assertTrue(organizationService.findByNameOrCode("").isEmpty(),
                "The item founded must be null");
    }


    @Test
    void givenValidId_whenFindById_thenFoundedOrganization() throws InstanceNotFoundException {
        Organization organization = OrganizationOM.withDefaultValues();
        organizationService.createOrganizationType(organization.getOrganizationType().getName());
        organization = organizationService.create(organization);

        Assertions.assertEquals(organization, organizationService.findById(organization.getId()));
    }

    @Test
    void givenInvalidName_whenFindById_thenInstanceNotFoundException() {

        Assertions.assertThrows(InstanceNotFoundException.class, () -> organizationService.findById(-1L), "InstanceNotFoundException not thrown");
    }

    @Test
    void givenValidId_whenFindByOrganizationTypeName_thenFoundedOrganization() {
        List<OrganizationType> organizationTypes = OrganizationTypeOM.withNames(Arrays.asList("Type1", "Type2", "Type3"));
        organizationTypes.forEach(organizationType ->
                organizationService.createOrganizationType(organizationType.getName()));


        List<Organization> organizations = new ArrayList<>();
        Organization organization = OrganizationOM.withOrganizationTypeAndRandomNames(organizationTypes.get(0).getName());
        organization = organizationService.create(organization);
        organizations.add(organization);

        organization = OrganizationOM.withOrganizationTypeAndRandomNames(organizationTypes.get(0).getName());
        organization = organizationService.create(organization);
        organizations.add(organization);

        organization = OrganizationOM.withOrganizationTypeAndRandomNames(organizationTypes.get(1).getName());
        organizationService.create(organization);


        Assertions.assertEquals(organizations.size(),
                organizationService.findByOrganizationTypeName(organizationTypes.get(0).getName()).size(),
                "The result must contain the same number of items");

        Assertions.assertTrue(
                organizationService.findByOrganizationTypeName(organizationTypes.get(0).getName()).containsAll(organizations),
                "The result must contain all items");
    }

    @Test
    void givenInvalidName_whenFindByOrganizationTypeName_thenReturnEmptyList() {

        Assertions.assertTrue(organizationService.findByOrganizationTypeName("").isEmpty(),
                "The result must be an empty list");
    }


    @Test
    void givenData_whenFindAllOrganizationTypes_thenReturnFoundedOrganizationType() {
        final OrganizationType organizationType = OrganizationTypeOM.withDefaultValues();
        organizationService.createOrganizationType(organizationType.getName());

        final List<OrganizationType> result = organizationService.findAllOrganizationTypes();

        Assertions.assertTrue(result.contains(organizationType), "Result must contain the same Data");
    }

    @Test
    void givenNoData_whenCallFindAll_thenReturnEmptyList() {
        final List<Organization> result = organizationService.findAll();

        Assertions.assertTrue(result.isEmpty(), "Result must be Empty");
    }

    @Test
    void givenData_whenFindAll_thenReturnNotEmptyList() {
        final OrganizationType organizationType = OrganizationTypeOM.withDefaultValues();
        organizationService.createOrganizationType(organizationType.getName());
        final Organization organization = OrganizationOM.withDefaultValues();
        organizationService.create(organization);

        final List<Organization> result = organizationService.findAll();

        Assertions.assertFalse(result.isEmpty(), "Result must be not empty");
        Assertions.assertTrue(result.contains(organization), "Result must contain the same Data");
    }

    @Test
    void givenValidId_whenDelete_thenDeletedSuccessfully() {
        final OrganizationType organizationType = OrganizationTypeOM.withDefaultValues();
        organizationService.createOrganizationType(organizationType.getName());

        Organization organization = OrganizationOM.withDefaultValues();
        organization = organizationService.create(organization);

        organizationService.deleteById(organization.getId());

        Assertions.assertTrue(organizationService.findAll().isEmpty(), "Expected result must be Empty");
    }


    @Test
    void givenValidData_whenUpdate_thenUpdatedSuccessfully() throws InstanceNotFoundException {
        final OrganizationType organizationType = OrganizationTypeOM.withDefaultValues();
        organizationService.createOrganizationType(organizationType.getName());

        Organization organization = OrganizationOM.withDefaultValues();
        organization = organizationService.create(organization);

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
        organizationService.createOrganizationType(organizationType.getName());

        Organization organization = OrganizationOM.withDefaultValues();
        organization = organizationService.create(organization);

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
        organizationService.createOrganizationType(organizationType.getName());

        Organization organization = OrganizationOM.withDefaultValues();
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
