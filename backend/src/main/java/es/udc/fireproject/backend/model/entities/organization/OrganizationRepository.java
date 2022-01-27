package es.udc.fireproject.backend.model.entities.organization;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface OrganizationRepository extends PagingAndSortingRepository<Organization, Long> {

    List<Organization> findByNameIgnoreCaseContainsOrCodeIgnoreCaseContains(String name, String code);

    List<Organization> findByOrganizationType_NameIgnoreCaseContains(String name);


}
