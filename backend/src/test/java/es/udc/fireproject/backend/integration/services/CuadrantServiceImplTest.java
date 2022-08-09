package es.udc.fireproject.backend.integration.services;

import es.udc.fireproject.backend.model.entities.cuadrant.Cuadrant;
import es.udc.fireproject.backend.model.services.cuadrant.CuadrantService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
public class CuadrantServiceImplTest {

    @Autowired
    CuadrantService cuadrantService;

    @Test
    void givenNoData_whenCallFindAll_thenReturnNotEmptyList() {
        Assertions.assertNotNull(cuadrantService.findAll());
    }

    @Test
    void givenValidData_whenCallFindByEscala_thenReturnNotEmptyList() {
        List<Cuadrant> cuadrantsScale50 = cuadrantService.findByEscala("50.0");
        Assertions.assertNotNull(cuadrantsScale50);
        List<Cuadrant> cuadrantsScale25 = cuadrantService.findByEscala("25.0");
        Assertions.assertNotNull(cuadrantsScale25);
        Assertions.assertNotEquals(cuadrantsScale50.size(), cuadrantsScale25.size());
    }

    @Test
    void givenInvalidData_whenCallFindByEscala_thenReturnNotEmptyList() {
        Assertions.assertNotNull(cuadrantService.findByEscala("Not a valid value"));
    }


}
