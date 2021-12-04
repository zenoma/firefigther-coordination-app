package es.udc.fireproject.backend.model.services.organization;

import es.udc.fireproject.backend.model.entities.organization.Organization;
import es.udc.fireproject.backend.model.entities.organization.OrganizationRepository;
import es.udc.fireproject.backend.model.entities.organization.OrganizationType;
import es.udc.fireproject.backend.model.entities.organization.OrganizationTypeRepository;
import es.udc.fireproject.backend.model.exceptions.InstanceNotFoundException;
import es.udc.fireproject.backend.model.services.utils.ConstraintValidator;
import org.locationtech.jts.geom.Geometry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private OrganizationTypeRepository organizationTypeRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Override
    public List<Organization> findByNameOrCode(String nameOrCode) {
        return organizationRepository.findByNameIgnoreCaseOrCode(nameOrCode, nameOrCode);
    }

    @Override
    public Optional<Organization> findById(Long id) {
        return organizationRepository.findById(id);
    }


    @Override
    public List<Organization> findByOrganizationTypeName(String organizationTypeName) {
        return organizationRepository.findByOrganizationType_Name(organizationTypeName);
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
    public Organization create(String code, String name, String headquartersAddress, Geometry location, String organizationTypeName) {
        OrganizationType organizationType = organizationTypeRepository.findByName(organizationTypeName);
        Organization organization = new Organization(code, name, headquartersAddress, location, organizationType);

        organization.setCreatedAt(LocalDateTime.now());
        ConstraintValidator.validate(organization);
        return organizationRepository.save(organization);
    }

    @Override
    public Organization create(Organization organization) {

        return create(organization.getCode(),
                organization.getName(),
                organization.getHeadquartersAddress(),
                organization.getLocation(),
                organization.getOrganizationType().getName());
    }


    @Override
    public void deleteById(Long id) {
        organizationRepository.deleteById(id);
    }

    @Override
    public Organization update(Long id, String name, String code, String headquartersAddress, Geometry location)
            throws InstanceNotFoundException {
        Optional<Organization> organizationOpt = organizationRepository.findById(id);

        if (organizationOpt.isEmpty()) {
            throw new InstanceNotFoundException("Organization not founded", id);

        } else {
            Organization organization = organizationOpt.get();
            organization.setName(name);
            organization.setCode(code);
            organization.setHeadquartersAddress(headquartersAddress);
            organization.setLocation(location);

            ConstraintValidator.validate(organization);
            return organizationRepository.save(organization);
        }
    }
}
