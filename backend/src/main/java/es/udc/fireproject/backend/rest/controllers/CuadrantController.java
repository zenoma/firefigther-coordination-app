package es.udc.fireproject.backend.rest.controllers;

import es.udc.fireproject.backend.model.entities.cuadrant.Cuadrant;
import es.udc.fireproject.backend.model.services.cuadrant.CuadrantService;
import es.udc.fireproject.backend.rest.dtos.CuadrantDto;
import es.udc.fireproject.backend.rest.dtos.conversors.CuadrantConversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/cuadrants")
public class CuadrantController {

    @Autowired
    CuadrantService cuadrantService;

    @GetMapping("")
    public List<CuadrantDto> findAll(@RequestAttribute Long userId, @RequestParam(required = false) String scale) {

        List<CuadrantDto> cuadrantDtos = new ArrayList<>();

        if (scale != null) {
            for (Cuadrant cuadrant : cuadrantService.findByEscala(scale)) {
                cuadrantDtos.add(CuadrantConversor.toCuadrantDto(cuadrant));
            }
        } else {
            for (Cuadrant cuadrant : cuadrantService.findAll()) {
                cuadrantDtos.add(CuadrantConversor.toCuadrantDto(cuadrant));
            }
        }
        return cuadrantDtos;
    }
}
