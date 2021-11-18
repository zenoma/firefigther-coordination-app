package es.udc.fireproject.backend.model.services.organization;

import es.udc.fireproject.backend.model.entities.organization.OrganizationType;
import es.udc.fireproject.backend.model.entities.organization.OrganizationTypeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private OrganizationTypeDao organizationTypeDao;

    @Override
    public List<OrganizationType> findAllOrganizationTypes() {
        Iterable<OrganizationType> iterable = organizationTypeDao.findAll();
        List<OrganizationType> result = new ArrayList<>();
        iterable.forEach(result::add);

        return result;
    }
}
