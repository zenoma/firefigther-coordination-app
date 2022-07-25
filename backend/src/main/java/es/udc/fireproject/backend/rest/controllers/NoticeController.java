package es.udc.fireproject.backend.rest.controllers;

import es.udc.fireproject.backend.model.entities.notice.Notice;
import es.udc.fireproject.backend.model.entities.notice.NoticeStatus;
import es.udc.fireproject.backend.model.exceptions.InstanceNotFoundException;
import es.udc.fireproject.backend.model.exceptions.NoticeStatusException;
import es.udc.fireproject.backend.model.services.notice.NoticeService;
import es.udc.fireproject.backend.rest.dtos.NoticeDto;
import es.udc.fireproject.backend.rest.dtos.conversors.NoticeConversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

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

    @GetMapping("/{id}")
    public NoticeDto findById(@RequestAttribute Long userId, @PathVariable Long id)
            throws InstanceNotFoundException {
        return NoticeConversor.toNoticeDto(noticeService.findById(id));
    }


    @GetMapping("")
    public List<NoticeDto> findAll(@RequestAttribute Long userId) {

        List<NoticeDto> noticeDtos = new ArrayList<>();
        for (Notice notice : noticeService.findByUserId(userId)) {
            noticeDtos.add(NoticeConversor.toNoticeDto(notice));
        }
        return noticeDtos;
    }

    @PutMapping("/{id}")
    public void update(@RequestAttribute Long userId,
                       @Validated({NoticeDto.AllValidations.class}) @RequestBody NoticeDto noticeDto,
                       @PathVariable Long id)
            throws InstanceNotFoundException, NoticeStatusException {

        Notice notice = NoticeConversor.toNotice(noticeDto);

        noticeService.update(id, notice.getBody(), notice.getLocation());

    }

    @DeleteMapping("/{id}")
    public void deleteById(@RequestAttribute Long userId, @PathVariable Long id) throws InstanceNotFoundException, NoticeStatusException {

        noticeService.deleteById(id);

    }

    @PutMapping("/{id}/changeStatus")
    public void checkNotice(@RequestAttribute Long userId,
                            @PathVariable Long id, @RequestParam("status") NoticeStatus status)
            throws InstanceNotFoundException, NoticeStatusException {

        noticeService.checkNotice(id, status);

    }

}
