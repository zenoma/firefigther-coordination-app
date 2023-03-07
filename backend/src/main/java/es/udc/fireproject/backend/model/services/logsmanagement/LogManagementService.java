package es.udc.fireproject.backend.model.services.logsmanagement;

import es.udc.fireproject.backend.model.entities.logs.FireQuadrantLog;
import es.udc.fireproject.backend.model.entities.logs.TeamQuadrantLog;
import es.udc.fireproject.backend.model.entities.logs.VehicleQuadrantLog;

import java.util.List;

public interface LogManagementService {

    List<FireQuadrantLog> findAllFireQuadrantLogs();

    List<TeamQuadrantLog> findAllTeamQuadrantLogs();

    List<VehicleQuadrantLog> findAllVehicleQuadrantLogs();

}
