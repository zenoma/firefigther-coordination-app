package es.udc.fireproject.backend.model.services;

import es.udc.fireproject.backend.model.entities.notice.Notice;
import es.udc.fireproject.backend.model.entities.notice.NoticeRepository;
import es.udc.fireproject.backend.model.entities.notice.NoticeStatus;
import es.udc.fireproject.backend.model.entities.user.UserRepository;
import es.udc.fireproject.backend.model.exceptions.InstanceNotFoundException;
import es.udc.fireproject.backend.model.exceptions.NoticeStatusException;
import es.udc.fireproject.backend.model.services.notice.NoticeServiceImpl;
import es.udc.fireproject.backend.utils.NoticeOm;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Point;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import java.util.Optional;

@SpringBootTest
@Transactional
class NoticeServiceImplTest {


    @Mock
    NoticeRepository noticeRepository;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    NoticeServiceImpl noticeService;

    @Test
    void givenValid_whenCreateNotice_thenCreatedSuccessfully() {

        Mockito.when(noticeRepository.save(Mockito.any())).thenReturn(NoticeOm.withDefaultValues());
        Notice notice = NoticeOm.withDefaultValues();
        notice = noticeService.create(notice.getBody(), notice.getLocation());

        Assertions.assertEquals(NoticeOm.withDefaultValues(), notice);

        Assertions.assertEquals(NoticeStatus.PENDING, notice.getStatus());

    }

    @Test
    void givenInvalid_whenCreateNotice_thenConstraintViolationException() {
        Notice notice = NoticeOm.withInvalidValues();
        String body = notice.getBody();
        Point location = notice.getLocation();
        Assertions.assertThrows(ConstraintViolationException.class,
                () -> noticeService.create(body,
                        location),
                "ConstraintViolationException must be thrown");

    }

    @Test
    void givenValid_whenUpdateNotice_thenCreatedSuccessfully() throws NoticeStatusException, InstanceNotFoundException {

        Mockito.when(noticeRepository.save(Mockito.any())).thenReturn(NoticeOm.withDefaultValues());
        Mockito.when(noticeRepository.findById(Mockito.any())).thenReturn(Optional.of(NoticeOm.withDefaultValues()));

        Notice notice = NoticeOm.withDefaultValues();
        notice = noticeService.create(notice.getBody(), notice.getLocation());
        Notice noticeUpdated = noticeService.update(notice.getId(), "New body", notice.getLocation());

        Assertions.assertEquals(notice, noticeUpdated);

        Assertions.assertEquals(NoticeStatus.PENDING, notice.getStatus());

    }

    @Test
    void givenAcceptedNotice_whenUpdateNotice_thenNoticeStatusException() {

        Mockito.when(noticeRepository.save(Mockito.any())).thenReturn(NoticeOm.withDefaultValues());

        Notice notice = NoticeOm.withDefaultValues();
        notice = noticeService.create(notice.getBody(), notice.getLocation());
        notice.setStatus(NoticeStatus.ACCEPTED);

        Mockito.when(noticeRepository.findById(Mockito.any())).thenReturn(Optional.of(notice));

        Notice finalNotice = notice;
        Assertions.assertThrows(NoticeStatusException.class, () -> noticeService.update(finalNotice.getId(), finalNotice.getBody(), finalNotice.getLocation()), "NoticeStatusException must be thrown");

    }

    @Test
    void givenNotCreated_whenUpdateNotice_thenInstanceNotFound() {

        Mockito.when(noticeRepository.save(Mockito.any())).thenReturn(NoticeOm.withDefaultValues());
        Mockito.when(noticeRepository.findById(Mockito.any())).thenReturn(Optional.empty());
        Notice notice = NoticeOm.withDefaultValues();

        Assertions.assertThrows(InstanceNotFoundException.class,
                () -> noticeService.update(notice.getId(), notice.getBody(), notice.getLocation()),
                "InstanceNotFoundException must be thrown");

    }

    @Test
    void givenValid_whenDeleteNotice_thenDeletedSuccessfully() throws NoticeStatusException, InstanceNotFoundException {

        Mockito.when(noticeRepository.save(Mockito.any())).thenReturn(NoticeOm.withDefaultValues());
        Mockito.when(noticeRepository.findById(Mockito.any())).thenReturn(Optional.of(NoticeOm.withDefaultValues()));
        Notice notice = NoticeOm.withDefaultValues();
        notice = noticeService.create(notice.getBody(), notice.getLocation());
        noticeService.deleteById(notice.getId());

        Mockito.when(noticeRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        Notice finalNotice = notice;
        Assertions.assertThrows(InstanceNotFoundException.class,
                () -> noticeService.findById(finalNotice.getId()),
                "InstanceNotFoundException must be thrown");

    }

    @Test
    void givenNotCreated_whenDeleteNotice_thenInstanceNotFoundException() {

        Mockito.when(noticeRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        Assertions.assertThrows(InstanceNotFoundException.class,
                () -> noticeService.deleteById(-1L),
                "InstanceNotFoundException must be thrown");

    }

    @Test
    void givenAcceptedNotice_whenDeleteNotice_thenNoticeStatusException() {

        Notice notice = NoticeOm.withDefaultValues();
        notice.setStatus(NoticeStatus.ACCEPTED);
        Mockito.when(noticeRepository.save(Mockito.any())).thenReturn(NoticeOm.withDefaultValues());
        Mockito.when(noticeRepository.findById(Mockito.any())).thenReturn(Optional.of(notice));


        Assertions.assertThrows(NoticeStatusException.class,
                () -> noticeService.deleteById(notice.getId()),
                "NoticeStatusException must be thrown");

    }

    @Test
    void givenValidNotice_whenCheckNotice_thenStatusChanged() throws NoticeStatusException, InstanceNotFoundException {

        Notice notice = NoticeOm.withDefaultValues();
        Mockito.when(noticeRepository.save(Mockito.any())).thenReturn(NoticeOm.withDefaultValues());
        Mockito.when(noticeRepository.findById(Mockito.any())).thenReturn(Optional.of(notice));

        notice = noticeService.create(notice.getBody(), notice.getLocation());

        noticeService.checkNotice(notice.getId(), NoticeStatus.ACCEPTED);
        notice.setStatus(NoticeStatus.ACCEPTED);
        Mockito.when(noticeRepository.findById(Mockito.any())).thenReturn(Optional.of(notice));

        Assertions.assertEquals(NoticeStatus.ACCEPTED, noticeService.findById(notice.getId()).getStatus(), "Status must be Accepted");


    }

    @Test
    void givenInvalidNotice_whenCheckNotice_thenInstanceNotFoundException() {
        Mockito.when(noticeRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        Assertions.assertThrows(InstanceNotFoundException.class,
                () -> noticeService.checkNotice(-1L, NoticeStatus.ACCEPTED),
                "InstanceNotFoundException must be thrown");


    }

    @Test
    void givenAcceptedNotice_whenCheckNotice_thenNoticeStatusException() {

        Notice notice = NoticeOm.withDefaultValues();
        Mockito.when(noticeRepository.save(Mockito.any())).thenReturn(NoticeOm.withDefaultValues());
        Mockito.when(noticeRepository.findById(Mockito.any())).thenReturn(Optional.of(notice));

        notice = noticeService.create(notice.getBody(), notice.getLocation());

        Notice finalNotice = notice;
        notice.setStatus(NoticeStatus.ACCEPTED);
        Mockito.when(noticeRepository.findById(Mockito.any())).thenReturn(Optional.of(notice));
        Assertions.assertThrows(NoticeStatusException.class,
                () -> noticeService.checkNotice(finalNotice.getId(), NoticeStatus.ACCEPTED),
                "NoticeStatusException must be thrown");

    }

}
