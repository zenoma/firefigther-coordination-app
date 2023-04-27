package es.udc.fireproject.backend.model.services.firemanagement;

import es.udc.fireproject.backend.model.entities.fire.Fire;
import es.udc.fireproject.backend.model.entities.fire.FireIndex;
import es.udc.fireproject.backend.model.entities.quadrant.Quadrant;
import es.udc.fireproject.backend.model.entities.team.Team;
import es.udc.fireproject.backend.model.entities.vehicle.Vehicle;
import es.udc.fireproject.backend.model.exceptions.ExtinguishedFireException;
import es.udc.fireproject.backend.model.exceptions.InstanceNotFoundException;
import es.udc.fireproject.backend.model.exceptions.TeamAlreadyDismantledException;
import es.udc.fireproject.backend.model.exceptions.VehicleAlreadyDismantledException;

import java.util.List;

public interface FireManagementService {
    // CUADRANT SERVICES
    List<Quadrant> findAllQuadrants();

    List<Quadrant> findQuadrantsByEscala(String scale);

    Quadrant findQuadrantById(Integer gid) throws InstanceNotFoundException;

    Quadrant linkFire(Integer gid, Long id) throws InstanceNotFoundException;

    List<Quadrant> findQuadrantsWithActiveFire();


    // FIRE SERVICES
    List<Fire> findAllFires();

    Fire findFireById(Long id) throws InstanceNotFoundException;

    Fire createFire(String description, String type, FireIndex fireIndex);

    Fire extinguishFire(Long id) throws InstanceNotFoundException, ExtinguishedFireException, TeamAlreadyDismantledException, VehicleAlreadyDismantledException;

    Fire extinguishQuadrantByFireId(Long id, Integer quadrantId) throws InstanceNotFoundException, ExtinguishedFireException, TeamAlreadyDismantledException, VehicleAlreadyDismantledException;

    Fire updateFire(Long id, String description, String type, FireIndex fireIndex) throws InstanceNotFoundException, ExtinguishedFireException;

    // EXTINCTION SERVICES

    Team deployTeam(Long teamId, Integer gid) throws InstanceNotFoundException, TeamAlreadyDismantledException;

    Team retractTeam(Long teamId) throws InstanceNotFoundException, TeamAlreadyDismantledException;

    Vehicle deployVehicle(Long vehicleId, Integer gid) throws InstanceNotFoundException, VehicleAlreadyDismantledException;

    Vehicle retractVehicle(Long vehicleId) throws InstanceNotFoundException, VehicleAlreadyDismantledException;


}
