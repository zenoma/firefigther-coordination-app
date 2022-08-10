package es.udc.fireproject.backend.rest.controllers;

import es.udc.fireproject.backend.model.entities.cuadrant.Cuadrant;
import es.udc.fireproject.backend.model.services.firemanagement.FireManagementService;
import es.udc.fireproject.backend.rest.dtos.CuadrantDto;
import es.udc.fireproject.backend.rest.dtos.conversors.CuadrantConversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/cuadrants")
public class CuadrantController {

    @Autowired
    FireManagementService fireManagementService;

    @GetMapping("")
    public List<CuadrantDto> findAll(@RequestParam(required = false) String scale) {

        List<CuadrantDto> cuadrantDtos = new ArrayList<>();

        if (scale != null) {
            for (Cuadrant cuadrant : fireManagementService.findCuadrantsByEscala(scale)) {
                cuadrantDtos.add(CuadrantConversor.toCuadrantDto(cuadrant));
            }
        } else {
            for (Cuadrant cuadrant : fireManagementService.findAllCuadrants()) {
                cuadrantDtos.add(CuadrantConversor.toCuadrantDto(cuadrant));
            }
        }
        return cuadrantDtos;
    }
}
