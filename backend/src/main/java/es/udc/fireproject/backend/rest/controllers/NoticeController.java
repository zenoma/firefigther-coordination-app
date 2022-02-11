package es.udc.fireproject.backend.rest.controllers;

import es.udc.fireproject.backend.model.entities.notice.Notice;
import es.udc.fireproject.backend.model.exceptions.InstanceNotFoundException;
import es.udc.fireproject.backend.model.services.notice.NoticeService;
import es.udc.fireproject.backend.rest.dtos.NoticeDto;
import es.udc.fireproject.backend.rest.dtos.conversors.NoticeConversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/notice")
public class NoticeController {

    @Autowired
    NoticeService noticeService;

    @PostMapping("/create")
    public ResponseEntity<NoticeDto> create(
            @Validated({NoticeDto.AllValidations.class}) @RequestBody NoticeDto noticeDto,
            @RequestAttribute(required = false) Long userId) throws InstanceNotFoundException {

        Notice notice = NoticeConversor.toNotice(noticeDto);

        if (userId != null) {
            notice = noticeService.create(notice.getBody(), notice.getLocation(), userId);
        } else {
            notice = noticeService.create(notice.getBody(), notice.getLocation());
        }


        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(notice.getId()).toUri();

        return ResponseEntity.created(location).body(NoticeConversor.toNoticeDto(notice));

    }


}
