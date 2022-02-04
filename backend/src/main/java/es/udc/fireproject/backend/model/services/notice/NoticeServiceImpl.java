package es.udc.fireproject.backend.model.services.notice;

import es.udc.fireproject.backend.model.entities.notice.Notice;
import es.udc.fireproject.backend.model.entities.notice.NoticeRepository;
import es.udc.fireproject.backend.model.exceptions.InstanceNotFoundException;
import es.udc.fireproject.backend.model.services.utils.ConstraintValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    NoticeRepository noticeRepository;

    @Override
    public Notice create(Notice notice) {
        ConstraintValidator.validate(notice);
        notice.setCreatedAt(LocalDateTime.now());
        return noticeRepository.save(notice);
    }

    @Override
    public Notice update(Notice notice) {
        ConstraintValidator.validate(notice);
        return noticeRepository.save(notice);
    }

    @Override
    public void deleteById(Long noticeId) {
        noticeRepository.deleteById(noticeId);
    }

    @Override
    public List<Notice> findByUserId(Long userId) {
        return noticeRepository.findByUserId(userId);
    }

    @Override
    public Notice findById(Long id) throws InstanceNotFoundException {

        Optional<Notice> noticeOpt = noticeRepository.findById(id);
        if (noticeOpt.isEmpty()) {
            throw new InstanceNotFoundException("Notice not found", id);
        }
        return noticeOpt.get();
    }

}
