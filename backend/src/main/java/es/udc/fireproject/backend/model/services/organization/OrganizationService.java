package es.udc.fireproject.backend.model.services.organization;

import es.udc.fireproject.backend.model.entities.organization.Organization;
import es.udc.fireproject.backend.model.entities.organization.OrganizationType;
import org.locationtech.jts.geom.Geometry;

import java.util.List;

public interface OrganizationService {

    Organization findByName(String name);

    Organization findByCode(String code);

    List<Organization> filterByOrganizationType(String organizationTypeName);

    List<OrganizationType> findAllOrganizationTypes();

    List<Organization> findAll();

    OrganizationType createOrganizationType(String name);

    Organization createOrganization(String code, String name, String headquartersAddress, Geometry location, String organizationTypeName);

    void deleteOrganization();

    Organization updateOrganization();


}
