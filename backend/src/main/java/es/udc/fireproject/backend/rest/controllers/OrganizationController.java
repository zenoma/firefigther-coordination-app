package es.udc.fireproject.backend.rest.controllers;

import es.udc.fireproject.backend.model.entities.organization.Organization;
import es.udc.fireproject.backend.model.entities.organization.OrganizationType;
import es.udc.fireproject.backend.model.exceptions.InstanceNotFoundException;
import es.udc.fireproject.backend.model.services.organization.OrganizationService;
import es.udc.fireproject.backend.rest.dtos.OrganizationDto;
import es.udc.fireproject.backend.rest.dtos.OrganizationTypeDto;
import es.udc.fireproject.backend.rest.dtos.UserDto;
import es.udc.fireproject.backend.rest.dtos.conversors.OrganizationConversor;
import es.udc.fireproject.backend.rest.dtos.conversors.OrganizationTypeConversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/organizations")
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;

    @GetMapping("/")
    public List<OrganizationDto> findAll(@RequestAttribute Long userId) {
        List<OrganizationDto> organizationDtos = new ArrayList<>();
        for (Organization organization : organizationService.findAll()) {
            organizationDtos.add(OrganizationConversor.toOrganizationDto(organization));
        }
        return organizationDtos;
    }

    @GetMapping("/organizationTypes")
    public List<OrganizationTypeDto> findAllOrganizationTypes(@RequestAttribute Long userId) {

        List<OrganizationTypeDto> organizationTypeDtos = new ArrayList<>();
        for (OrganizationType organizationType : organizationService.findAllOrganizationTypes()) {
            organizationTypeDtos.add(OrganizationTypeConversor.toOrganizationTypeDto(organizationType));
        }
        return organizationTypeDtos;
    }

    @GetMapping("/organizationTypes/{id}")
    public OrganizationTypeDto findAllOrganizationTypes(@RequestAttribute Long userId, @PathVariable Long id) throws InstanceNotFoundException {
        OrganizationType organizationType = organizationService.findOrganizationTypeById(id);
        return OrganizationTypeConversor.toOrganizationTypeDto(organizationType);

    }

    @PostMapping("/create")
    public OrganizationDto create(@RequestAttribute Long userId,
                                  @Validated({UserDto.AllValidations.class})
                                  @RequestBody OrganizationDto organizationDto)
            throws InstanceNotFoundException {

        OrganizationType organizationType;
        organizationType = organizationService.findOrganizationTypeById(organizationDto.getOrganizationTypeId());
        Organization organization = OrganizationConversor.toOrganization(organizationDto, organizationType);

        organization = organizationService.create(organization);

        return OrganizationConversor.toOrganizationDto(organization);

    }

}
