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
    public FireQuadrantLog logLinkedFire(Long fireId, Integer quadrantId) throws InstanceNotFoundException {

        Quadrant quadrant = fireManagementService.findQuadrantById(quadrantId);

        Fire fire = fireManagementService.findFireById(fireId);

        return fireQuadrantLogRepository.save(new FireQuadrantLog(fire, quadrant, LocalDateTime.now()));

    }

    @Override
    public FireQuadrantLog logExtinguishedFire(Long fireId, Integer quadrantId) {
        FireQuadrantLog fireQuadrantLog = fireQuadrantLogRepository.findByFireIdAndQuadrantId(fireId, quadrantId);

        fireQuadrantLog.setExtinguishedAt(LocalDateTime.now());

        return fireQuadrantLogRepository.save(fireQuadrantLog);
    }

    @Override
    public TeamQuadrantLog logDeployedTeam(Long teamId, Integer quadrantId) throws InstanceNotFoundException {
        Quadrant quadrant = fireManagementService.findQuadrantById(quadrantId);

        Team team = personalManagementService.findTeamById(teamId);

        return teamQuadrantLogRepository.save(new TeamQuadrantLog(team, quadrant, LocalDateTime.now()));
    }

    @Override
    public TeamQuadrantLog logRetractedTeam(Long teamId, Integer quadrantId) {
        TeamQuadrantLog teamQuadrantLog = teamQuadrantLogRepository.findByTeamIdAndQuadrantIdAndRetractAtIsNull(teamId, quadrantId);

        teamQuadrantLog.setRetractAt(LocalDateTime.now());


        return teamQuadrantLogRepository.save(teamQuadrantLog);
    }

    @Override
    public VehicleQuadrantLog logDeployedVehicle(Long vehicleId, Integer quadrantId) throws InstanceNotFoundException {
        Quadrant quadrant = fireManagementService.findQuadrantById(quadrantId);

        Vehicle vehicle = personalManagementService.findVehicleById(vehicleId);

        return vehicleQuadrantLogRepository.save(new VehicleQuadrantLog(vehicle, quadrant, LocalDateTime.now()));
    }

    @Override
    public VehicleQuadrantLog logRetractedVehicle(Long vehicleId, Integer quadrantId) {
        VehicleQuadrantLog vehicleQuadrantLog = vehicleQuadrantLogRepository.findByVehicleIdAndQuadrantIdAndRetractAtIsNull(vehicleId, quadrantId);

        vehicleQuadrantLog.setRetractAt(LocalDateTime.now());


        return vehicleQuadrantLogRepository.save(vehicleQuadrantLog);
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
