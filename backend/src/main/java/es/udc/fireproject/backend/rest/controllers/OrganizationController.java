package es.udc.fireproject.backend.rest.controllers;

import es.udc.fireproject.backend.model.entities.organization.Organization;
import es.udc.fireproject.backend.model.entities.organization.OrganizationType;
import es.udc.fireproject.backend.model.exceptions.InstanceNotFoundException;
import es.udc.fireproject.backend.model.services.organization.OrganizationService;
import es.udc.fireproject.backend.rest.dtos.OrganizationConversor;
import es.udc.fireproject.backend.rest.dtos.OrganizationDto;
import es.udc.fireproject.backend.rest.dtos.OrganizationTypeDto;
import es.udc.fireproject.backend.rest.dtos.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/organizations")
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;

    @GetMapping("/")
    public OrganizationDto findAll(@RequestAttribute Long userId) {
        // TODO Not implemented yed
        throw new UnsupportedOperationException("Not implemented yed");
    }

    @GetMapping("/allTypes")
    public OrganizationTypeDto findAllOrganizationTypes(@RequestAttribute Long userId) {

        List<OrganizationType> organizationTypeList = organizationService.findAllOrganizationTypes();
        return new OrganizationTypeDto(organizationTypeList);

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

    @GetMapping("/init")
    public List<Organization> getShops() {
        return organizationService.findAll();
    }
}
