package es.udc.fireproject.backend.model.services.organization;

import es.udc.fireproject.backend.model.entities.organization.Organization;
import es.udc.fireproject.backend.model.entities.organization.OrganizationType;
import es.udc.fireproject.backend.model.exceptions.InstanceNotFoundException;
import org.locationtech.jts.geom.Geometry;

import java.util.List;
import java.util.Optional;

public interface OrganizationService {

    List<Organization> findByNameOrCode(String nameOrCode);

    Optional<Organization> findById(Long id);

    List<Organization> findByOrganizationTypeName(String organizationTypeName);

    List<OrganizationType> findAllOrganizationTypes();

    List<Organization> findAll();

    OrganizationType createOrganizationType(String name);

    Organization create(String code, String name, String headquartersAddress, Geometry location, String organizationTypeName);

    Organization create(Organization organization);

    void deleteById(Long id);

    Organization update(Long id, String name, String code, String headquartersAddress, Geometry location) throws InstanceNotFoundException;
}
