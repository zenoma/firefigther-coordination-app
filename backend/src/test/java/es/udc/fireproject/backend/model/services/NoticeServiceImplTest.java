package es.udc.fireproject.backend.model.services;

import es.udc.fireproject.backend.model.entities.notice.Notice;
import es.udc.fireproject.backend.model.entities.notice.NoticeRepository;
import es.udc.fireproject.backend.model.services.notice.NoticeServiceImpl;
import es.udc.fireproject.backend.utils.NoticeOm;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class NoticeServiceImplTest {


    @Mock
    NoticeRepository noticeRepository;

    @InjectMocks
    NoticeServiceImpl noticeService;

    @Test
    void givenValid_whenCreateNotice_thenCreatedSuccessfully() {

        Mockito.when(noticeRepository.save(Mockito.any())).thenReturn(NoticeOm.withDefaultValues());

        Notice notice = noticeService.create(NoticeOm.withDefaultValues());

        Assertions.assertEquals(NoticeOm.withDefaultValues(), notice);

    }

}
