package es.udc.fireproject.backend.model.services.organization;

import es.udc.fireproject.backend.model.entities.organization.Organization;
import es.udc.fireproject.backend.model.entities.organization.OrganizationType;

import java.util.List;

public interface OrganizationService {

    List<OrganizationType> findAllOrganizationTypes();

    List<Organization> findAll();
}
