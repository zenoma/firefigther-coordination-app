package es.udc.fireproject.backend.integration.services;

import es.udc.fireproject.backend.model.entities.quadrant.Quadrant;
import es.udc.fireproject.backend.model.services.firemanagement.FireManagementService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
class FireManagementServiceImplTest {

    @Autowired
    FireManagementService fireManagementService;

    @Test
    void givenNoData_whenCallFindAll_thenReturnNotEmptyList() {
        Assertions.assertNotNull(fireManagementService.findAllQuadrants());
    }

    @Test
    void givenValidData_whenCallFindByEscala_thenReturnNotEmptyList() {
        List<Quadrant> quadrantsScale50 = fireManagementService.findQuadrantsByEscala("50.0");
        Assertions.assertNotNull(quadrantsScale50);
        List<Quadrant> quadrantsScale25 = fireManagementService.findQuadrantsByEscala("25.0");
        Assertions.assertNotNull(quadrantsScale25);
        Assertions.assertNotEquals(quadrantsScale50.size(), quadrantsScale25.size());
    }

    @Test
    void givenInvalidData_whenCallFindByEscala_thenReturnNotEmptyList() {
        Assertions.assertNotNull(fireManagementService.findQuadrantsByEscala("Not a valid value"));
    }


}
