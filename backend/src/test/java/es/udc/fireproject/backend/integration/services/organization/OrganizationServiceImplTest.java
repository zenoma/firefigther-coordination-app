package es.udc.fireproject.backend.integration.services.organization;

import es.udc.fireproject.backend.model.entities.organization.Organization;
import es.udc.fireproject.backend.model.entities.organization.OrganizationType;
import es.udc.fireproject.backend.model.services.organization.OrganizationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

class OrganizationTypeOM {
    static OrganizationType withDefaultValues() {
        return new OrganizationType("Dummy Name");
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
}

@SpringBootTest
@Transactional
class OrganizationServiceImplTest {

    @Autowired
    OrganizationService organizationService;

    @Test
    void givenValidData_whenCreationOrganization_thenReturnOrganizationWithId() {

        OrganizationType organizationType = OrganizationTypeOM.withDefaultValues();
        organizationService.createOrganizationType(organizationType.getName());

        Organization organization = OrganizationOM.withDefaultValues();

        Organization result = organizationService.create(organization.getCode(),
                organization.getName(),
                organization.getHeadquartersAddress(),
                organization.getLocation(),
                organization.getOrganizationType().getName());

        Assertions.assertNotNull(result.getId(), "Id must be not Null");
        Assertions.assertNotNull(result.getCreatedAt(), "Created date must be not Null");

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

        Assertions.assertTrue(organizationService.findByNameOrCode("").isEmpty(), "The item founded must be null");
    }
}
