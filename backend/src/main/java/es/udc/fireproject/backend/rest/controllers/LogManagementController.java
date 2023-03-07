package es.udc.fireproject.backend.rest.controllers;

import es.udc.fireproject.backend.model.entities.logs.FireQuadrantLog;
import es.udc.fireproject.backend.model.entities.logs.TeamQuadrantLog;
import es.udc.fireproject.backend.model.entities.logs.VehicleQuadrantLog;
import es.udc.fireproject.backend.model.services.logsmanagement.LogManagementService;
import es.udc.fireproject.backend.rest.dtos.FireQuadrantLogDto;
import es.udc.fireproject.backend.rest.dtos.TeamQuadrantLogDto;
import es.udc.fireproject.backend.rest.dtos.VehicleQuadrantLogDto;
import es.udc.fireproject.backend.rest.dtos.conversors.FireQuadrantLogConversor;
import es.udc.fireproject.backend.rest.dtos.conversors.TeamQuadrantLogConversor;
import es.udc.fireproject.backend.rest.dtos.conversors.VehicleQuadrantLogConversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/logs")
public class LogManagementController {

    @Autowired
    LogManagementService logManagementService;

    @GetMapping("/fires")
    public List<FireQuadrantLogDto> findAllFiresLogs(@RequestAttribute Long userId) {

        List<FireQuadrantLogDto> fireQuadrantLogDtos = new ArrayList<>();

        for (FireQuadrantLog fireQuadrantLog : logManagementService.findAllFireQuadrantLogs()) {
            fireQuadrantLogDtos.add(FireQuadrantLogConversor.toFireQuadrantLogDto(fireQuadrantLog));
        }

        return fireQuadrantLogDtos;
    }

    @GetMapping("/teams")
    public List<TeamQuadrantLogDto> findAllTeamsLogs(@RequestAttribute Long userId) {

        List<TeamQuadrantLogDto> teamQuadrantLogDtos = new ArrayList<>();

        for (TeamQuadrantLog teamQuadrantLog : logManagementService.findAllTeamQuadrantLogs()) {
            teamQuadrantLogDtos.add(TeamQuadrantLogConversor.toTeamQuadrantLogDto(teamQuadrantLog));
        }

        return teamQuadrantLogDtos;

    }

    @GetMapping("/vehicles")
    public List<VehicleQuadrantLogDto> findAllVehiclesLogs(@RequestAttribute Long userId) {


        List<VehicleQuadrantLogDto> vehicleQuadrantLogDtos = new ArrayList<>();

        for (VehicleQuadrantLog vehicleQuadrantLog : logManagementService.findAllVehicleQuadrantLogs()) {
            vehicleQuadrantLogDtos.add(VehicleQuadrantLogConversor.toVehicleQuadrantDto(vehicleQuadrantLog));
        }

        return vehicleQuadrantLogDtos;
    }
}
