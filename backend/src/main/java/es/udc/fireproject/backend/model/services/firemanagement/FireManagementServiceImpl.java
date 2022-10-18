package es.udc.fireproject.backend.model.services.firemanagement;

import es.udc.fireproject.backend.model.entities.cuadrant.Cuadrant;
import es.udc.fireproject.backend.model.entities.cuadrant.CuadrantRepository;
import es.udc.fireproject.backend.model.entities.fire.Fire;
import es.udc.fireproject.backend.model.entities.fire.FireIndex;
import es.udc.fireproject.backend.model.entities.fire.FireRepository;
import es.udc.fireproject.backend.model.entities.team.Team;
import es.udc.fireproject.backend.model.entities.team.TeamRepository;
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

    public static final String CUADRANT_NOT_FOUND = "Cuadrant not found";
    public static final String FIRE_NOT_FOUND = "Fire not found";
    public static final String TEAM_NOT_FOUND = "Team not found";


    @Autowired
    CuadrantRepository cuadrantRepository;

    @Autowired
    FireRepository fireRepository;

    @Autowired
    TeamRepository teamRepository;

    // CUADRANT SERVICES
    @Override
    public List<Cuadrant> findAllCuadrants() {
        return cuadrantRepository.findAll();
    }

    @Override
    public List<Cuadrant> findCuadrantsByEscala(String scale) {
        return cuadrantRepository.findByEscala(scale);
    }

    @Override
    public Cuadrant findCuadrantById(Integer gid) throws InstanceNotFoundException {
        return cuadrantRepository.findById(gid).orElseThrow(() -> new InstanceNotFoundException(CUADRANT_NOT_FOUND, gid));

    }

    @Override
    public Cuadrant linkFire(Integer gid, Long id) throws InstanceNotFoundException {

        Cuadrant cuadrant = cuadrantRepository.findById(gid).orElseThrow(() -> new InstanceNotFoundException(CUADRANT_NOT_FOUND, gid));

        Fire fire = fireRepository.findById(id).orElseThrow(() -> new InstanceNotFoundException(FIRE_NOT_FOUND, id));

        cuadrant.setFire(fire);

        return cuadrantRepository.save(cuadrant);
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

        List<Cuadrant> cuadrants = cuadrantRepository.findByFireId(id);

        for (Cuadrant cuadrant : cuadrants
        ) {
            cuadrant.setFire(null);
            cuadrantRepository.save(cuadrant);
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
    public Team deploy(Long teamId, Integer gid) throws InstanceNotFoundException {
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new InstanceNotFoundException(TEAM_NOT_FOUND, teamId));
        Cuadrant cuadrant = cuadrantRepository.findById(gid).orElseThrow(() -> new InstanceNotFoundException(CUADRANT_NOT_FOUND, gid));

        team.setCuadrant(cuadrant);

        return teamRepository.save(team);

    }

    @Override
    public Team retract(Long teamId) throws InstanceNotFoundException {
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new InstanceNotFoundException(TEAM_NOT_FOUND, teamId));

        team.setCuadrant(null);

        return teamRepository.save(team);

    }


}
