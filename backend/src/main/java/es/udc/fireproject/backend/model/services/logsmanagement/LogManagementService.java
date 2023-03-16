package es.udc.fireproject.backend.model.services.logsmanagement;

import es.udc.fireproject.backend.model.entities.logs.FireQuadrantLog;
import es.udc.fireproject.backend.model.entities.logs.TeamQuadrantLog;
import es.udc.fireproject.backend.model.entities.logs.VehicleQuadrantLog;
import es.udc.fireproject.backend.model.exceptions.InstanceNotFoundException;

import java.util.List;

public interface LogManagementService {

    FireQuadrantLog logFire(Long fireId, Integer quadrantId) throws InstanceNotFoundException;

    TeamQuadrantLog logTeam(Long teamId, Integer quadrantId) throws InstanceNotFoundException;

    VehicleQuadrantLog logVehicle(Long vehicleId, Integer quadrantId) throws InstanceNotFoundException;


    List<FireQuadrantLog> findAllFireQuadrantLogs();

    List<TeamQuadrantLog> findAllTeamQuadrantLogs();

    List<VehicleQuadrantLog> findAllVehicleQuadrantLogs();

}
