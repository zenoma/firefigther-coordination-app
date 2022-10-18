package es.udc.fireproject.backend.model.services.notice;

import es.udc.fireproject.backend.model.entities.image.Image;
import es.udc.fireproject.backend.model.entities.image.ImageRepository;
import es.udc.fireproject.backend.model.entities.notice.Notice;
import es.udc.fireproject.backend.model.entities.notice.NoticeRepository;
import es.udc.fireproject.backend.model.entities.notice.NoticeStatus;
import es.udc.fireproject.backend.model.entities.user.User;
import es.udc.fireproject.backend.model.entities.user.UserRepository;
import es.udc.fireproject.backend.model.exceptions.ImageAlreadyUploadedException;
import es.udc.fireproject.backend.model.exceptions.InstanceNotFoundException;
import es.udc.fireproject.backend.model.exceptions.NoticeStatusException;
import es.udc.fireproject.backend.model.services.utils.ConstraintValidator;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NoticeServiceImpl implements NoticeService {

    public static final String NOTICE_NOT_FOUND = "Notice not found";
    @Autowired
    NoticeRepository noticeRepository;

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public Notice create(String body, Point location) {
        Notice notice = new Notice(body, NoticeStatus.PENDING, location);
        notice.setCreatedAt(LocalDateTime.now());

        ConstraintValidator.validate(notice);

        return noticeRepository.save(notice);
    }

    @Override
    public Notice create(String body, Point location, Long userId) throws InstanceNotFoundException {
        Notice notice = new Notice(body, NoticeStatus.PENDING, location);

        User user = userRepository.findById(userId).orElseThrow(() -> new InstanceNotFoundException("User not found", userId));

        notice.setUser(user);
        notice.setCreatedAt(LocalDateTime.now());

        ConstraintValidator.validate(notice);

        return noticeRepository.save(notice);
    }

    @Override
    public Notice update(Long id, String body, Point location) throws NoticeStatusException, InstanceNotFoundException {

        Notice notice = noticeRepository.findById(id).orElseThrow(() -> new InstanceNotFoundException(NOTICE_NOT_FOUND, id));

        if (notice.getStatus() != NoticeStatus.PENDING) {
            throw new NoticeStatusException(notice.getId(), "can not be updated, current status: " + notice.getStatus());
        }
        notice.setBody(body);
        notice.setLocation(location);

        ConstraintValidator.validate(notice);
        return noticeRepository.save(notice);
    }

    @Override
    public void deleteById(Long id) throws InstanceNotFoundException, NoticeStatusException {

        Notice notice = noticeRepository.findById(id).orElseThrow(() -> new InstanceNotFoundException(NOTICE_NOT_FOUND, id));

        if (notice.getStatus() != NoticeStatus.PENDING) {
            throw new NoticeStatusException(notice.getId(), "can not be deleted, current status: " + notice.getStatus());
        }
        noticeRepository.deleteById(id);
    }

    @Override
    public List<Notice> findByUserId(Long userId) {
        return noticeRepository.findByUserId(userId);
    }

    @Override
    public Notice findById(Long id) throws InstanceNotFoundException {

        return noticeRepository.findById(id).orElseThrow(() -> new InstanceNotFoundException(NOTICE_NOT_FOUND, id));
    }

    @Override
    public void checkNotice(Long id, NoticeStatus status) throws InstanceNotFoundException, NoticeStatusException {

        Notice notice = noticeRepository.findById(id).orElseThrow(() -> new InstanceNotFoundException(NOTICE_NOT_FOUND, id));


        if (notice.getStatus() == NoticeStatus.REJECTED || notice.getStatus() == NoticeStatus.ACCEPTED) {
            throw new NoticeStatusException(notice.getId(), "can not be checked, current status: " + notice.getStatus());
        }
        notice.setStatus(status);
    }

    @Override
    public Notice addImage(Long id, String name) throws InstanceNotFoundException, ImageAlreadyUploadedException {
        Notice notice = noticeRepository.findById(id).orElseThrow(() -> new InstanceNotFoundException(NOTICE_NOT_FOUND, id));


        Image image = imageRepository.findByName(name);
        if (image != null) {
            throw new ImageAlreadyUploadedException(image.getId(), "another image with that name is already uploaded");
        }

        image = new Image(notice, name);
        imageRepository.save(image);

        return notice;

    }

}
