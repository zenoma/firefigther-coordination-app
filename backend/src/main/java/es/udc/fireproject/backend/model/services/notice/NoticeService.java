package es.udc.fireproject.backend.model.services.notice;

import es.udc.fireproject.backend.model.entities.notice.Notice;
import es.udc.fireproject.backend.model.exceptions.InstanceNotFoundException;

import java.util.List;

public interface NoticeService {

    Notice create(Notice notice);

    Notice update(Notice notice);

    void deleteById(Long noticeId);

    List<Notice> findByUserId(Long userId);

    Notice findById(Long id) throws InstanceNotFoundException;

}
