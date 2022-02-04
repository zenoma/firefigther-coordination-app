package es.udc.fireproject.backend.integration.services;

import es.udc.fireproject.backend.model.entities.notice.Notice;
import es.udc.fireproject.backend.model.exceptions.InstanceNotFoundException;
import es.udc.fireproject.backend.model.services.notice.NoticeService;
import es.udc.fireproject.backend.utils.NoticeOm;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class NoticeServiceImplTest {


    @Autowired
    NoticeService noticeService;

    @Test
    void givenValid_whenCreateNotice_thenCreatedSuccessfully() throws InstanceNotFoundException {

        Notice notice = noticeService.create(NoticeOm.withDefaultValues());

        Assertions.assertEquals(NoticeOm.withDefaultValues(), notice);

        Assertions.assertEquals(noticeService.findById(notice.getId()), notice);
    }

}