package es.udc.fireproject.backend.model.services.organization;

import es.udc.fireproject.backend.model.entities.organization.Organization;
import es.udc.fireproject.backend.model.entities.organization.OrganizationType;
import es.udc.fireproject.backend.model.exceptions.InstanceNotFoundException;
import org.locationtech.jts.geom.Point;

import java.util.List;

public interface OrganizationService {

    OrganizationType createOrganizationType(String name);

    OrganizationType findOrganizationTypeById(Long id) throws InstanceNotFoundException;

    List<OrganizationType> findAllOrganizationTypes();

    List<Organization> findByNameOrCode(String nameOrCode);

    Organization findById(Long id) throws InstanceNotFoundException;

    List<Organization> findByOrganizationTypeName(String organizationTypeName);

    List<Organization> findAll();

    Organization create(String code, String name, String headquartersAddress, Point location, String organizationTypeName);

    Organization create(Organization organization);

    void deleteById(Long id);

    Organization update(Long id, String name, String code, String headquartersAddress, Point location) throws InstanceNotFoundException;
}
