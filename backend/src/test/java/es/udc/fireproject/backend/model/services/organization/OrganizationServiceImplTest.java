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

import java.util.ArrayList;
import java.util.List;


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
    @Mock
    OrganizationTypeRepository organizationTypeRepository;

    @Mock
    OrganizationRepository organizationRepository;

    @InjectMocks
    OrganizationServiceImpl organizationService;


    @Test
    void givenValidData_whenCreationOrganization_thenReturnOrganizationWithId() {

        Organization dummyOrganization = OrganizationOM.aDummyOrganization();

        dummyOrganization.setId(1L);

        Mockito.when(organizationRepository.save(Mockito.any())).thenReturn(dummyOrganization);
        Mockito.when(organizationTypeRepository.findByName(Mockito.anyString())).thenReturn(OrganizationTypeOM.aDummyOrganizationType());

        Organization result = organizationService.createOrganization(dummyOrganization.getCode(),
                dummyOrganization.getName(),
                dummyOrganization.getHeadquartersAddress(),
                dummyOrganization.getLocation(),
                dummyOrganization.getOrganizationType().getName());

        Assertions.assertNotNull(result.getId(), "Id must be not Null");

        // Create At must be null because the Object never persist in the Database
        Assertions.assertNull(result.getCreatedAt(), "Created date must be Null");

    }


    @Test
    void givenNoData_whenCallFindAllOrganizationTypes_thenReturnEmptyList() {
        List<OrganizationType> result = organizationService.findAllOrganizationTypes();

        Assertions.assertTrue(result.isEmpty(), "Result must be Empty");
    }

    @Test
    void givenData_whenFindAllOrganizationTypes_thenReturnNotEmptyList() {
        OrganizationType organizationType = new OrganizationType();
        organizationType.setId(1L);
        organizationType.setName("name");

        List<OrganizationType> list = new ArrayList<>();
        list.add(organizationType);

        Mockito.when(organizationTypeRepository.findAll()).thenReturn(list);

        List<OrganizationType> result = organizationService.findAllOrganizationTypes();

        Assertions.assertTrue(result.contains(list.get(0)), "Result must contain the same Data");
    }

    @Test
    void givenNoData_whenCallFindAll_thenReturnEmptyList() {
        List<Organization> result = organizationService.findAll();

        Assertions.assertTrue(result.isEmpty(), "Result must be Empty");
    }

    @Test
    void givenData_whenFindAll_thenReturnNotEmptyList() {
        final OrganizationType organizationType = new OrganizationType();
        organizationType.setId(1L);
        organizationType.setName("name");

        final Organization organization = new Organization();
        organization.setId(1L);
        organization.setName("Centro Coordinación Central");
        organization.setHeadquartersAddress("Calle alguna");
        organization.setOrganizationType(organizationType);

        final GeometryFactory geoFactory = new GeometryFactory();
        organization.setLocation(geoFactory.createPoint(new Coordinate(-45, 45)));


        final List<Organization> list = new ArrayList<>();
        list.add(organization);

        Mockito.when(organizationRepository.findAll()).thenReturn(list);

        List<Organization> result = organizationService.findAll();

        Assertions.assertFalse(result.isEmpty(), "Result must be not empty");
        Assertions.assertTrue(result.contains(list.get(0)), "Result must contain the same Data");
    }


}