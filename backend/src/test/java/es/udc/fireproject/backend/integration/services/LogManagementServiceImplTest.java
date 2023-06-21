package es.udc.fireproject.backend.integration.services;

import es.udc.fireproject.backend.model.entities.fire.Fire;
import es.udc.fireproject.backend.model.entities.organization.Organization;
import es.udc.fireproject.backend.model.entities.organization.OrganizationType;
import es.udc.fireproject.backend.model.entities.quadrant.Quadrant;
import es.udc.fireproject.backend.model.entities.team.Team;
import es.udc.fireproject.backend.model.entities.vehicle.Vehicle;
import es.udc.fireproject.backend.model.exceptions.AlreadyDismantledException;
import es.udc.fireproject.backend.model.exceptions.AlreadyExistException;
import es.udc.fireproject.backend.model.exceptions.ExtinguishedFireException;
import es.udc.fireproject.backend.model.exceptions.InstanceNotFoundException;
import es.udc.fireproject.backend.model.services.firemanagement.FireManagementServiceImpl;
import es.udc.fireproject.backend.model.services.logsmanagement.LogManagementService;
import es.udc.fireproject.backend.model.services.personalmanagement.PersonalManagementServiceImpl;
import es.udc.fireproject.backend.utils.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@SpringBootTest
@Transactional
public class LogManagementServiceImplTest {

    private static final Integer VALID_QUADRANT_ID = 1;
    private static final Integer INVALID_QUADRANT_ID = -1;

    @Autowired
    LogManagementService logManagementService;

    @Autowired
    FireManagementServiceImpl fireManagementService;

    @Autowired
    PersonalManagementServiceImpl personalManagementService;

    @Test
    void givenNoData_whenFindAllFireQuadrantLogs_thenReturnNotEmptyList() {
        Assertions.assertNotNull(logManagementService.findAllFireQuadrantLogs());
    }

    @Test
    void givenNoData_whenFindAllTeamQuadrantLogs_thenReturnNotEmptyList() {
        Assertions.assertNotNull(logManagementService.findAllTeamQuadrantLogs());
    }

    @Test
    void givenNoData_whenFindAllVehicleQuadrantLogs_thenReturnNotEmptyList() {
        Assertions.assertNotNull(logManagementService.findAllVehicleQuadrantLogs());
    }

    @Test
    void givenValidData_whenFindFiresLogByFireIdAndLinkedAt_thenReturnValidLogs() throws InstanceNotFoundException, ExtinguishedFireException, AlreadyDismantledException {
        Fire fire = FireOM.withDefaultValues();
        fire = fireManagementService.createFire(fire.getDescription(), fire.getType(), fire.getFireIndex());

        Quadrant quadrant = fireManagementService.findQuadrantById(VALID_QUADRANT_ID);
        Quadrant quadrant2 = fireManagementService.findQuadrantById(2);
        Quadrant quadrant3 = fireManagementService.findQuadrantById(3);


        LocalDateTime starDate = LocalDateTime.now();
        quadrant = fireManagementService.linkFire(quadrant.getId(), fire.getId());
        quadrant2 = fireManagementService.linkFire(quadrant2.getId(), fire.getId());
        quadrant3 = fireManagementService.linkFire(quadrant3.getId(), fire.getId());

        LocalDateTime endDate = LocalDateTime.now();


        fire = fireManagementService.extinguishQuadrantByFireId(fire.getId(), quadrant.getId());
        fire = fireManagementService.extinguishQuadrantByFireId(fire.getId(), quadrant2.getId());

        logManagementService.findFiresByFireIdAndLinkedAt(fire.getId(), starDate, endDate);

        Assertions.assertNotNull(logManagementService.findFiresByFireIdAndLinkedAt(fire.getId(), starDate, endDate));
    }

    @Test
    void givenValidData_whenFindTeamsByQuadrantIdAndDeployAtBetweenOrderByDeployAt_thenReturnValidLogs() throws InstanceNotFoundException, ExtinguishedFireException, AlreadyDismantledException, AlreadyExistException {
        Fire fire = FireOM.withDefaultValues();
        fire = fireManagementService.createFire(fire.getDescription(), fire.getType(), fire.getFireIndex());

        OrganizationType organizationType = personalManagementService.createOrganizationType(OrganizationTypeOM.withDefaultValues().getName());
        Organization organization = OrganizationOM.withDefaultValues();
        organization.setOrganizationType(organizationType);
        organization = personalManagementService.createOrganization(organization);

        Quadrant quadrant = fireManagementService.findQuadrantById(VALID_QUADRANT_ID);
        Quadrant quadrant2 = fireManagementService.findQuadrantById(2);
        Quadrant quadrant3 = fireManagementService.findQuadrantById(3);

        Team team = TeamOM.withDefaultValues();
        team = personalManagementService.createTeam(team.getCode(),
                organization.getId());

        Vehicle vehicle = VehicleOM.withDefaultValues();
        vehicle = personalManagementService.createVehicle(vehicle.getVehiclePlate(), vehicle.getType(),
                organization.getId());


        LocalDateTime starDate = LocalDateTime.now();
        quadrant = fireManagementService.linkFire(quadrant.getId(), fire.getId());
        quadrant2 = fireManagementService.linkFire(quadrant2.getId(), fire.getId());
        quadrant3 = fireManagementService.linkFire(quadrant3.getId(), fire.getId());

        vehicle = fireManagementService.deployVehicle(vehicle.getId(), quadrant.getId());
        team = fireManagementService.deployTeam(team.getId(), quadrant.getId());

        vehicle = fireManagementService.deployVehicle(vehicle.getId(), quadrant2.getId());
        team = fireManagementService.deployTeam(team.getId(), quadrant2.getId());

        vehicle = fireManagementService.deployVehicle(vehicle.getId(), quadrant3.getId());
        team = fireManagementService.deployTeam(team.getId(), quadrant3.getId());
        LocalDateTime endDate = LocalDateTime.now();


        Assertions.assertEquals(1, logManagementService.findTeamsByQuadrantIdAndDeployAtBetweenOrderByDeployAt(quadrant.getId(), starDate, endDate).size());
        Assertions.assertEquals(1, logManagementService.findTeamsByQuadrantIdAndDeployAtBetweenOrderByDeployAt(quadrant2.getId(), starDate, endDate).size());


        Assertions.assertEquals(1, logManagementService.findVehiclesByQuadrantIdAndDeployAtBetweenOrderByDeployAt(quadrant.getId(), starDate, endDate).size());
        Assertions.assertEquals(1, logManagementService.findVehiclesByQuadrantIdAndDeployAtBetweenOrderByDeployAt(quadrant2.getId(), starDate, endDate).size());

    }

}
