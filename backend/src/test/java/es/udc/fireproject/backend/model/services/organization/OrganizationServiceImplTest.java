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


@SpringBootTest
class OrganizationServiceImplTest {
    @Mock
    OrganizationTypeRepository organizationTypeRepository;

    @Mock
    OrganizationRepository organizationRepository;

    @InjectMocks
    OrganizationServiceImpl organizationService;

    @Test
    void givenNoData_whenCallFindAllOrganizationTypes_thenReturnEmptyList() {
        List<OrganizationType> result = organizationService.findAllOrganizationTypes();

        Assertions.assertTrue(result.isEmpty(), "Result must be Empty");
    }

    @Test
    void givenData_whenFindAllOrganizationTypes_thenReturnNotEmptyList() {
        OrganizationType organizationType = new OrganizationType();
        organizationType.setId(1L);
        organizationType.setOrganizationTypeName("name");

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
        organizationType.setOrganizationTypeName("name");

        final Organization organization = new Organization();
        organization.setId(1L);
        organization.setOrganizationName("Centro Coordinación Central");
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