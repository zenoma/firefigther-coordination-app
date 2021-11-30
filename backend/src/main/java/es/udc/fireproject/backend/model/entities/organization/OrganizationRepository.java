package es.udc.fireproject.backend.model.entities.organization;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface OrganizationRepository extends PagingAndSortingRepository<Organization, Long> {

    List<Organization> findByNameIgnoreCaseOrCode(String name, String code);

    List<Organization> findByOrganizationType_Name(String name);


}
