package es.udc.fireproject.backend.model.services.logsmanagement;

import es.udc.fireproject.backend.model.entities.fire.Fire;
import es.udc.fireproject.backend.model.entities.logs.*;
import es.udc.fireproject.backend.model.entities.quadrant.Quadrant;
import es.udc.fireproject.backend.model.entities.team.Team;
import es.udc.fireproject.backend.model.entities.vehicle.Vehicle;
import es.udc.fireproject.backend.model.exceptions.InstanceNotFoundException;
import es.udc.fireproject.backend.model.services.firemanagement.FireManagementServiceImpl;
import es.udc.fireproject.backend.model.services.personalmanagement.PersonalManagementServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class LogManagementServiceImpl implements LogManagementService {

    @Autowired
    FireQuadrantLogRepository fireQuadrantLogRepository;
    @Autowired
    TeamQuadrantLogRepository teamQuadrantLogRepository;
    @Autowired
    VehicleQuadrantLogRepository vehicleQuadrantLogRepository;

    @Autowired
    FireManagementServiceImpl fireManagementService;
    @Autowired
    PersonalManagementServiceImpl personalManagementService;


    @Override
    public FireQuadrantLog logFire(Long fireId, Integer quadrantId) throws InstanceNotFoundException {

        Quadrant quadrant = fireManagementService.findQuadrantById(quadrantId);

        Fire fire = fireManagementService.findFireById(fireId);

        return fireQuadrantLogRepository.save(new FireQuadrantLog(fire, quadrant, quadrant.getLinkedAt(), LocalDateTime.now()));

    }


    @Override
    public TeamQuadrantLog logTeam(Long teamId, Integer quadrantId) throws InstanceNotFoundException {
        Quadrant quadrant = fireManagementService.findQuadrantById(quadrantId);
        Team team = personalManagementService.findTeamById(teamId);

        return teamQuadrantLogRepository.save(new TeamQuadrantLog(team, quadrant, team.getDeployAt(), LocalDateTime.now()));

    }


    @Override
    public VehicleQuadrantLog logVehicle(Long vehicleId, Integer quadrantId) throws InstanceNotFoundException {
        Quadrant quadrant = fireManagementService.findQuadrantById(quadrantId);
        Vehicle vehicle = personalManagementService.findVehicleById(vehicleId);

        return vehicleQuadrantLogRepository.save(new VehicleQuadrantLog(vehicle, quadrant, vehicle.getDeployAt(), LocalDateTime.now()));
    }


    public List<FireQuadrantLog> findAllFireQuadrantLogs() {
        return fireQuadrantLogRepository.findAll();
    }

    public List<TeamQuadrantLog> findAllTeamQuadrantLogs() {
        return teamQuadrantLogRepository.findAll();
    }

    public List<VehicleQuadrantLog> findAllVehicleQuadrantLogs() {
        return vehicleQuadrantLogRepository.findAll();
    }


}
