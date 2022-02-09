package es.udc.fireproject.backend.model.entities.notice;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface NoticeRepository extends PagingAndSortingRepository<Notice, Long> {

    List<Notice> findByUserId(Long userId);

}
