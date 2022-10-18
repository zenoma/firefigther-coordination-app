package es.udc.fireproject.backend.model.services.firemanagement;

import es.udc.fireproject.backend.model.entities.cuadrant.Cuadrant;
import es.udc.fireproject.backend.model.entities.fire.Fire;
import es.udc.fireproject.backend.model.entities.fire.FireIndex;
import es.udc.fireproject.backend.model.entities.team.Team;
import es.udc.fireproject.backend.model.entities.vehicle.Vehicle;
import es.udc.fireproject.backend.model.exceptions.ExtinguishedFireException;
import es.udc.fireproject.backend.model.exceptions.InstanceNotFoundException;

import java.util.List;

public interface FireManagementService {
    // CUADRANT SERVICES
    List<Cuadrant> findAllCuadrants();

    List<Cuadrant> findCuadrantsByEscala(String scale);

    Cuadrant findCuadrantById(Integer gid) throws InstanceNotFoundException;

    Cuadrant linkFire(Integer gid, Long id) throws InstanceNotFoundException;


    // FIRE SERVICES
    List<Fire> findAllFires();

    Fire findFireById(Long id) throws InstanceNotFoundException;

    Fire createFire(String description, String type, FireIndex fireIndex);

    Fire extinguishFire(Long id) throws InstanceNotFoundException;

    Fire updateFire(Long id, String description, String type, FireIndex fireIndex) throws InstanceNotFoundException, ExtinguishedFireException;

    // EXTINCTION SERVICES

    Team deployTeam(Long teamId, Integer gid) throws InstanceNotFoundException;

    Team retractTeam(Long teamId) throws InstanceNotFoundException;

    Vehicle deployVehicle(Long vehicleId, Integer gid) throws InstanceNotFoundException;

    Vehicle retractVehicle(Long vehicleId) throws InstanceNotFoundException;


}
