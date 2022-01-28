package es.udc.fireproject.backend.rest.controllers;

import es.udc.fireproject.backend.model.entities.organization.Organization;
import es.udc.fireproject.backend.model.exceptions.InstanceNotFoundException;
import es.udc.fireproject.backend.model.services.organization.OrganizationService;
import es.udc.fireproject.backend.rest.dtos.OrganizationDto;
import es.udc.fireproject.backend.rest.dtos.UserDto;
import es.udc.fireproject.backend.rest.dtos.conversors.OrganizationConversor;
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


    @GetMapping("")
    public List<OrganizationDto> findAll(@RequestAttribute Long userId,
                                         @RequestParam(required = false) String nameOrCode,
                                         @RequestParam(required = false) String organizationTypeName) {

        List<OrganizationDto> organizationDtos = new ArrayList<>();
        if (nameOrCode != null) {
            for (Organization organization : organizationService.findByNameOrCode(nameOrCode)) {
                organizationDtos.add(OrganizationConversor.toOrganizationDto(organization));
            }
        } else if (organizationTypeName != null) {
            for (Organization organization : organizationService.findByOrganizationTypeName(organizationTypeName)) {
                organizationDtos.add(OrganizationConversor.toOrganizationDto(organization));
            }
        } else {
            for (Organization organization : organizationService.findAll()) {
                organizationDtos.add(OrganizationConversor.toOrganizationDto(organization));
            }
        }
        return organizationDtos;
    }

    @GetMapping("/{id}")
    public OrganizationDto findById(@RequestAttribute Long userId, @PathVariable Long id)
            throws InstanceNotFoundException {
        return OrganizationConversor.toOrganizationDto(organizationService.findById(id));
    }


    @PostMapping("/{id}/delete")
    public void deleteById(@RequestAttribute Long userId, @PathVariable Long id) {
        organizationService.deleteById(id);
    }

    @PostMapping("/create")
    public OrganizationDto create(@RequestAttribute Long userId,
                                  @Validated({UserDto.AllValidations.class})
                                  @RequestBody OrganizationDto organizationDto)
            throws InstanceNotFoundException {


        Organization organization = OrganizationConversor.toOrganization(organizationDto);

        organization = organizationService.create(organization);

        return OrganizationConversor.toOrganizationDto(organization);
    }

    @PutMapping("/{id}")
    public void update(@RequestAttribute Long userId,
                       @Validated({UserDto.AllValidations.class})
                       @RequestBody OrganizationDto organizationDto,
                       @PathVariable Long id)
            throws InstanceNotFoundException {
        Organization organization = OrganizationConversor.toOrganization(organizationDto);

        organizationService.update(id, organization.getName(),
                organization.getCode(),
                organization.getHeadquartersAddress(),
                organization.getLocation());

    }


}
