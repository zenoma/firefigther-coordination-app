package es.udc.fireproject.backend.model.services.logsmanagement;

import es.udc.fireproject.backend.model.entities.logs.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
