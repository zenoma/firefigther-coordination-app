package es.udc.fireproject.backend.model.services.notice;

import es.udc.fireproject.backend.model.entities.notice.Notice;
import es.udc.fireproject.backend.model.entities.notice.NoticeStatus;
import es.udc.fireproject.backend.model.exceptions.ImageAlreadyUploadedException;
import es.udc.fireproject.backend.model.exceptions.InstanceNotFoundException;
import es.udc.fireproject.backend.model.exceptions.NoticeStatusException;
import org.locationtech.jts.geom.Point;

import java.util.List;

public interface NoticeService {


    Notice create(String body, Point location);

    Notice create(String body, Point location, Long userId) throws InstanceNotFoundException;

    Notice update(Long id, String body, Point location) throws NoticeStatusException, InstanceNotFoundException;

    void deleteById(Long noticeId) throws InstanceNotFoundException, NoticeStatusException;

    List<Notice> findByUserId(Long userId);

    Notice findById(Long id) throws InstanceNotFoundException;

    void checkNotice(Long id, NoticeStatus status) throws InstanceNotFoundException, NoticeStatusException;

    Notice addImage(Long id, String name) throws InstanceNotFoundException, ImageAlreadyUploadedException;

}
