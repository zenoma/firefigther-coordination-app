package es.udc.fireproject.backend.rest.controllers;

import es.udc.fireproject.backend.model.entities.cuadrant.Cuadrant;
import es.udc.fireproject.backend.model.exceptions.InstanceNotFoundException;
import es.udc.fireproject.backend.model.services.firemanagement.FireManagementService;
import es.udc.fireproject.backend.rest.dtos.CuadrantDto;
import es.udc.fireproject.backend.rest.dtos.CuadrantInfoDto;
import es.udc.fireproject.backend.rest.dtos.conversors.CuadrantConversor;
import es.udc.fireproject.backend.rest.dtos.conversors.CuadrantInfoConversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @GetMapping("/{gid}")
    public CuadrantInfoDto findCuadrantById(@PathVariable Integer gid)
            throws InstanceNotFoundException {
        return CuadrantInfoConversor.toCuadrantDto(fireManagementService.findCuadrantById(gid));
    }

    @PostMapping("/{gid}/linkFire")
    public void linkFire(@RequestAttribute Long userId, @PathVariable Integer gid, @RequestBody Map<String, String> jsonParams)
            throws InstanceNotFoundException {

        fireManagementService.linkFire(gid, Long.valueOf(jsonParams.get("fireId")));
    }


}
