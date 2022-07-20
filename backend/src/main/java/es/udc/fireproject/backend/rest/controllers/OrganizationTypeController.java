package es.udc.fireproject.backend.rest.controllers;

import es.udc.fireproject.backend.model.entities.organization.OrganizationType;
import es.udc.fireproject.backend.model.exceptions.InstanceNotFoundException;
import es.udc.fireproject.backend.model.services.organization.OrganizationService;
import es.udc.fireproject.backend.rest.dtos.OrganizationTypeDto;
import es.udc.fireproject.backend.rest.dtos.UserDto;
import es.udc.fireproject.backend.rest.dtos.conversors.OrganizationTypeConversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/organizationTypes")
public class OrganizationTypeController {

    @Autowired
    private OrganizationService organizationService;

    @GetMapping("/")
    public List<OrganizationTypeDto> findAllOrganizationTypes(@RequestAttribute Long userId) {

        List<OrganizationTypeDto> organizationTypeDtos = new ArrayList<>();
        for (OrganizationType organizationType : organizationService.findAllOrganizationTypes()) {
            organizationTypeDtos.add(OrganizationTypeConversor.toOrganizationTypeDto(organizationType));
        }
        return organizationTypeDtos;
    }

    @GetMapping("/{id}")
    public OrganizationTypeDto findAllOrganizationTypes(@RequestAttribute Long userId, @PathVariable Long id)
            throws InstanceNotFoundException {
        OrganizationType organizationType = organizationService.findOrganizationTypeById(id);
        return OrganizationTypeConversor.toOrganizationTypeDto(organizationType);

    }

    @PostMapping("/create")
    public OrganizationTypeDto create(@RequestAttribute Long userId,
                                      @Validated({UserDto.AllValidations.class})
                                      @RequestBody OrganizationTypeDto organizationTypeDto) {

        OrganizationType organizationType = organizationService.createOrganizationType(organizationTypeDto.getName());

        return OrganizationTypeConversor.toOrganizationTypeDto(organizationType);
    }


}
