package es.udc.fireproject.backend.model.entities.organization;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface OrganizationRepository extends PagingAndSortingRepository<Organization, Long> {
    
    Organization findByName(String name);
}
