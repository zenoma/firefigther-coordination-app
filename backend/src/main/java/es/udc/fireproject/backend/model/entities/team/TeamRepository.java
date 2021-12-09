package es.udc.fireproject.backend.model.entities.team;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface TeamRepository extends PagingAndSortingRepository<Team, Long> {

    List<Team> findByCode(String code);


}
