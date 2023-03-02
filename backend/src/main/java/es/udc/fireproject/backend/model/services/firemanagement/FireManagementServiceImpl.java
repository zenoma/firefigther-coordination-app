package es.udc.fireproject.backend.model.services.firemanagement;

import es.udc.fireproject.backend.model.entities.fire.Fire;
import es.udc.fireproject.backend.model.entities.fire.FireIndex;
import es.udc.fireproject.backend.model.entities.fire.FireRepository;
import es.udc.fireproject.backend.model.entities.quadrant.Quadrant;
import es.udc.fireproject.backend.model.entities.quadrant.QuadrantRepository;
import es.udc.fireproject.backend.model.entities.team.Team;
import es.udc.fireproject.backend.model.entities.team.TeamRepository;
import es.udc.fireproject.backend.model.entities.vehicle.Vehicle;
import es.udc.fireproject.backend.model.entities.vehicle.VehicleRepository;
import es.udc.fireproject.backend.model.exceptions.ExtinguishedFireException;
import es.udc.fireproject.backend.model.exceptions.InstanceNotFoundException;
import es.udc.fireproject.backend.model.services.utils.ConstraintValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class FireManagementServiceImpl implements FireManagementService {

    public static final String QUADRANT_NOT_FOUND = "Quadrant not found";
    public static final String FIRE_NOT_FOUND = "Fire not found";
    public static final String TEAM_NOT_FOUND = "Team not found";
    public static final String VEHICLE_NOT_FOUND = "Vehicle not found";


    @Autowired
    QuadrantRepository quadrantRepository;

    @Autowired
    FireRepository fireRepository;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    VehicleRepository vehicleRepository;

    // CUADRANT SERVICES
    @Override
    public List<Quadrant> findAllQuadrants() {
        return quadrantRepository.findAll();
    }

    @Override
    public List<Quadrant> findQuadrantsByEscala(String scale) {
        return quadrantRepository.findByEscala(scale);
    }

    @Override
    public Quadrant findQuadrantById(Integer gid) throws InstanceNotFoundException {
        return quadrantRepository.findById(gid).orElseThrow(() -> new InstanceNotFoundException(QUADRANT_NOT_FOUND, gid));

    }

    @Override
    public Quadrant linkFire(Integer gid, Long id) throws InstanceNotFoundException {

        Quadrant quadrant = quadrantRepository.findById(gid).orElseThrow(() -> new InstanceNotFoundException(QUADRANT_NOT_FOUND, gid));

        Fire fire = fireRepository.findById(id).orElseThrow(() -> new InstanceNotFoundException(FIRE_NOT_FOUND, id));

        quadrant.setFire(fire);

        return quadrantRepository.save(quadrant);
    }


    // FIRE SERVICES
    @Override
    public List<Fire> findAllFires() {
        return fireRepository.findAll();
    }

    @Override
    public Fire findFireById(Long id) throws InstanceNotFoundException {

        return fireRepository.findById(id).orElseThrow(() -> new InstanceNotFoundException(FIRE_NOT_FOUND, id));
    }

    @Override
    public Fire createFire(String description, String type, FireIndex fireIndex) {
        Fire fire = new Fire(description, type, fireIndex);
        fire.setCreatedAt((LocalDateTime.now()));
        fire.setFireIndex(FireIndex.ZERO);

        ConstraintValidator.validate(fire);

        return fireRepository.save(fire);
    }

    @Override
    public Fire extinguishFire(Long id) throws InstanceNotFoundException {

        Fire fire = fireRepository.findById(id).orElseThrow(() -> new InstanceNotFoundException(FIRE_NOT_FOUND, id));

        fire.setFireIndex(FireIndex.EXTINGUISHED);
        fire.setExtinguishedAt(LocalDateTime.now());

        List<Quadrant> quadrants = quadrantRepository.findByFireId(id);

        for (Quadrant quadrant : quadrants
        ) {
            quadrant.setFire(null);
            quadrantRepository.save(quadrant);
        }

        return fireRepository.save(fire);
    }

    @Override
    public Fire updateFire(Long id, String description, String type, FireIndex fireIndex) throws InstanceNotFoundException, ExtinguishedFireException {

        Fire fire = fireRepository.findById(id).orElseThrow(() -> new InstanceNotFoundException(FIRE_NOT_FOUND, id));

        if (fireIndex == FireIndex.EXTINGUISHED) {
            throw new ExtinguishedFireException(id, ": fireIndex can not be update to EXTINGUISHED");
        }

        if (fire.getFireIndex() != FireIndex.EXTINGUISHED) {
            fire.setDescription(description);
            fire.setType(type);
            fire.setFireIndex(fireIndex);
        } else {
            throw new ExtinguishedFireException(id, "can not be updated");
        }

        return fireRepository.save(fire);
    }


    // EXTINCTION SERVICES
    @Override
    public Team deployTeam(Long teamId, Integer gid) throws InstanceNotFoundException {
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new InstanceNotFoundException(TEAM_NOT_FOUND, teamId));
        Quadrant quadrant = quadrantRepository.findById(gid).orElseThrow(() -> new InstanceNotFoundException(QUADRANT_NOT_FOUND, gid));

        team.setQuadrant(quadrant);

        return teamRepository.save(team);

    }

    @Override
    public Team retractTeam(Long teamId) throws InstanceNotFoundException {
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new InstanceNotFoundException(TEAM_NOT_FOUND, teamId));

        team.setQuadrant(null);

        return teamRepository.save(team);

    }

    @Override
    public Vehicle deployVehicle(Long vehicleId, Integer gid) throws InstanceNotFoundException {
        Vehicle vehicle = vehicleRepository.findById(vehicleId).orElseThrow(() -> new InstanceNotFoundException(VEHICLE_NOT_FOUND, vehicleId));
        Quadrant quadrant = quadrantRepository.findById(gid).orElseThrow(() -> new InstanceNotFoundException(QUADRANT_NOT_FOUND, gid));

        vehicle.setQuadrant(quadrant);

        return vehicleRepository.save(vehicle);

    }

    @Override
    public Vehicle retractVehicle(Long vehicleId) throws InstanceNotFoundException {
        Vehicle vehicle = vehicleRepository.findById(vehicleId).orElseThrow(() -> new InstanceNotFoundException(VEHICLE_NOT_FOUND, vehicleId));

        vehicle.setQuadrant(null);

        return vehicleRepository.save(vehicle);

    }


}
