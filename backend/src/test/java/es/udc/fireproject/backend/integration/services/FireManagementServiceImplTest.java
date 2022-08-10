package es.udc.fireproject.backend.integration.services;

import es.udc.fireproject.backend.model.entities.cuadrant.Cuadrant;
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
        Assertions.assertNotNull(fireManagementService.findAllCuadrants());
    }

    @Test
    void givenValidData_whenCallFindByEscala_thenReturnNotEmptyList() {
        List<Cuadrant> cuadrantsScale50 = fireManagementService.findCuadrantsByEscala("50.0");
        Assertions.assertNotNull(cuadrantsScale50);
        List<Cuadrant> cuadrantsScale25 = fireManagementService.findCuadrantsByEscala("25.0");
        Assertions.assertNotNull(cuadrantsScale25);
        Assertions.assertNotEquals(cuadrantsScale50.size(), cuadrantsScale25.size());
    }

    @Test
    void givenInvalidData_whenCallFindByEscala_thenReturnNotEmptyList() {
        Assertions.assertNotNull(fireManagementService.findCuadrantsByEscala("Not a valid value"));
    }


}
