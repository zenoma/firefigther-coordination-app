package es.udc.fireproject.backend.model.services.logsmanagement;

import es.udc.fireproject.backend.model.entities.logs.FireQuadrantLog;
import es.udc.fireproject.backend.model.entities.logs.TeamQuadrantLog;
import es.udc.fireproject.backend.model.entities.logs.VehicleQuadrantLog;
import es.udc.fireproject.backend.model.exceptions.InstanceNotFoundException;

import java.util.List;

public interface LogManagementService {

    FireQuadrantLog logLinkedFire(Long fireId, Integer quadrantId) throws InstanceNotFoundException;

    FireQuadrantLog logExtinguishedFire(Long fireId, Integer quadrantId);

    TeamQuadrantLog logDeployedTeam(Long teamId, Integer quadrantId) throws InstanceNotFoundException;

    TeamQuadrantLog logRetractedTeam(Long teamId, Integer quadrantId);

    VehicleQuadrantLog logDeployedVehicle(Long vehicleId, Integer quadrantId) throws InstanceNotFoundException;

    VehicleQuadrantLog logRetractedVehicle(Long vehicleId, Integer quadrantId);


    List<FireQuadrantLog> findAllFireQuadrantLogs();

    List<TeamQuadrantLog> findAllTeamQuadrantLogs();

    List<VehicleQuadrantLog> findAllVehicleQuadrantLogs();

}
