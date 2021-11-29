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

class OrganizationTypeOM {
    static OrganizationType aDummyOrganizationType() {
        return new OrganizationType("Dummy Name");
    }
}

class OrganizationOM {
    static Organization aDummyOrganization() {
        final GeometryFactory geoFactory = new GeometryFactory();

        return new Organization("ORG-01",
                "Centro de Coordinación Central",
                "Calle alguna", geoFactory.createPoint(new Coordinate(-45, 45)),
                OrganizationTypeOM.aDummyOrganizationType());
    }
}

@SpringBootTest
class OrganizationServiceImplTest {

    @Autowired
    OrganizationService organizationService;

    @Test
    void givenValidData_whenCreationOrganization_thenReturnOrganizationWithId() {

        Organization dummyOrganization = OrganizationOM.aDummyOrganization();
        Organization createdOrganization = dummyOrganization;

        Organization result = organizationService.createOrganization(dummyOrganization.getCode(),
                dummyOrganization.getName(),
                dummyOrganization.getHeadquartersAddress(),
                dummyOrganization.getLocation(),
                dummyOrganization.getOrganizationType().getName());

        Assertions.assertNotNull(result.getId(), "Id must be not Null");
        Assertions.assertNotNull(result.getCreatedAt(), "Created date must be not Null");

    }
}
