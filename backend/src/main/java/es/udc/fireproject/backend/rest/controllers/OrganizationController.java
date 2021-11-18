package es.udc.fireproject.backend.rest.controllers;

import es.udc.fireproject.backend.model.entities.organization.OrganizationType;
import es.udc.fireproject.backend.model.services.organization.OrganizationService;
import es.udc.fireproject.backend.rest.dtos.OrganizationTypeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/organizations")
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;

    @GetMapping("/list")
    public List<OrganizationTypeDto> findAll() {

        List<OrganizationType> query = organizationService.findAllOrganizationTypes();
        List<OrganizationTypeDto> result = new ArrayList<>();
        for (OrganizationType var : query) {
            result.add(new OrganizationTypeDto(var.getOrganizationTypeName()));
        }

        return result;

    }
}
