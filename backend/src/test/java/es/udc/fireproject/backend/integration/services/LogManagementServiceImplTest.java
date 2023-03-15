package es.udc.fireproject.backend.integration.services;

import es.udc.fireproject.backend.model.services.logsmanagement.LogManagementService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class LogManagementServiceImplTest {

    @Autowired
    LogManagementService logManagementService;

    @Test
    void givenNoData_whenCallFindAll_thenReturnNotEmptyList() {
        Assertions.assertNotNull(logManagementService.findAllFireQuadrantLogs());
    }
}
