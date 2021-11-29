package es.udc.fireproject.backend.model.services.organization;

import es.udc.fireproject.backend.model.entities.organization.Organization;
import es.udc.fireproject.backend.model.entities.organization.OrganizationRepository;
import es.udc.fireproject.backend.model.entities.organization.OrganizationType;
import es.udc.fireproject.backend.model.entities.organization.OrganizationTypeRepository;
import es.udc.fireproject.backend.model.services.utils.ConstraintValidator;
import org.locationtech.jts.geom.Geometry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private OrganizationTypeRepository organizationTypeRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Override
    public Organization findByName(String name) {
        return organizationRepository.findByName(name);
    }

    @Override
    public Organization findByCode(String code) {

        // TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Organization> filterByOrganizationType(String organizationTypeName) {
        // TODO
        throw new UnsupportedOperationException();
    }

    public List<OrganizationType> findAllOrganizationTypes() {

        List<OrganizationType> organizationTypes = new ArrayList<>();

        organizationTypeRepository.findAll().forEach(organizationTypes::add);
        return organizationTypes;
    }

    @Override
    public List<Organization> findAll() {
        List<Organization> organizations = new ArrayList<>();

        organizationRepository.findAll().forEach(organizations::add);
        return organizations;
    }

    @Override
    public OrganizationType createOrganizationType(String name) {
        OrganizationType organizationType = new OrganizationType();
        organizationType.setName(name);

        ConstraintValidator.validate(organizationType);
        return organizationTypeRepository.save(organizationType);
    }

    @Override
    public Organization createOrganization(String code, String name, String headquartersAddress, Geometry location, String organizationTypeName) {
        OrganizationType organizationType = organizationTypeRepository.findByName(organizationTypeName);
        Organization organization = new Organization(code, name, headquartersAddress, location, organizationType);

        organization.setCreatedAt(LocalDateTime.now());
        ConstraintValidator.validate(organization);
        return organizationRepository.save(organization);
    }

    @Override
    public Organization createOrganization(Organization organization) {

        return createOrganization(organization.getCode(),
                organization.getName(),
                organization.getHeadquartersAddress(),
                organization.getLocation(),
                organization.getOrganizationType().getName());
    }


    @Override
    public void deleteOrganization() {
        // TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public Organization updateOrganization() {
        return null;
    }
}
