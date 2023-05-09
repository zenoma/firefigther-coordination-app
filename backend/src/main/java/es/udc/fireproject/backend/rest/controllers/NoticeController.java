package es.udc.fireproject.backend.rest.controllers;

import es.udc.fireproject.backend.model.entities.notice.Notice;
import es.udc.fireproject.backend.model.entities.notice.NoticeStatus;
import es.udc.fireproject.backend.model.exceptions.*;
import es.udc.fireproject.backend.model.services.notice.NoticeService;
import es.udc.fireproject.backend.rest.common.ErrorsDto;
import es.udc.fireproject.backend.rest.common.FileUploadUtil;
import es.udc.fireproject.backend.rest.dtos.NoticeDto;
import es.udc.fireproject.backend.rest.dtos.NoticeStatusDto;
import es.udc.fireproject.backend.rest.dtos.conversors.NoticeConversor;
import es.udc.fireproject.backend.rest.exceptions.ImageRequiredException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/notices")
public class NoticeController {

    private static final String IMAGE_ALREADY_UPLOADED_EXCEPTION_CODE = "project.exceptions.ImageAlreadyUploadedException";
    private static final String NOTICE_CHECK_STATUS_EXCEPTION_CODE = "project.exceptions.NoticeCheckStatusException";
    private static final String NOTICE_UPDATE_STATUS_EXCEPTION_CODE = "project.exceptions.NoticeUpdateStatusException";
    private static final String NOTICE_DELETE_STATUS_EXCEPTION_CODE = "project.exceptions.NoticeDeleteStatusException";


    @Autowired
    private MessageSource messageSource;


    @ExceptionHandler(ImageAlreadyUploadedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorsDto handleImageAlreadyUploadedException(ImageAlreadyUploadedException exception, Locale locale) {

        String errorMessage = messageSource.getMessage(IMAGE_ALREADY_UPLOADED_EXCEPTION_CODE,
                new Object[]{exception.getId()}, IMAGE_ALREADY_UPLOADED_EXCEPTION_CODE, locale);

        return new ErrorsDto(errorMessage);
    }


    @ExceptionHandler(NoticeCheckStatusException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorsDto handleNoticeCheckStatusException(NoticeCheckStatusException exception, Locale locale) {

        String errorMessage = messageSource.getMessage(NOTICE_CHECK_STATUS_EXCEPTION_CODE,
                new Object[]{exception.getId(), exception.getStatus()}, NOTICE_CHECK_STATUS_EXCEPTION_CODE, locale);

        return new ErrorsDto(errorMessage);

    }

    @ExceptionHandler(NoticeUpdateStatusException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorsDto handleNoticeUpdateStatusException(NoticeUpdateStatusException exception, Locale locale) {

        String errorMessage = messageSource.getMessage(NOTICE_UPDATE_STATUS_EXCEPTION_CODE,
                new Object[]{exception.getId(), exception.getStatus()}, NOTICE_UPDATE_STATUS_EXCEPTION_CODE, locale);

        return new ErrorsDto(errorMessage);

    }

    @ExceptionHandler(NoticeDeleteStatusException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorsDto handleNoticeDeleteStatusException(NoticeDeleteStatusException exception, Locale locale) {

        String errorMessage = messageSource.getMessage(NOTICE_DELETE_STATUS_EXCEPTION_CODE,
                new Object[]{exception.getId(), exception.getStatus()}, NOTICE_DELETE_STATUS_EXCEPTION_CODE, locale);

        return new ErrorsDto(errorMessage);

    }


    @Autowired
    NoticeService noticeService;

    @PostMapping("")
    public ResponseEntity<NoticeDto> create(@Validated({NoticeDto.AllValidations.class}) @RequestBody NoticeDto noticeDto,
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
    public List<NoticeDto> findAll(@RequestAttribute Long userId, @RequestParam(value = "id", required = false) Long id) {

        List<NoticeDto> noticeDtos = new ArrayList<>();
        if (id != null) {
            for (Notice notice : noticeService.findByUserId(id)) {
                noticeDtos.add(NoticeConversor.toNoticeDto(notice));
            }
        } else {
            for (Notice notice : noticeService.findAll()) {
                noticeDtos.add(NoticeConversor.toNoticeDto(notice));
            }
        }


        return noticeDtos;
    }


    @PutMapping("/{id}")
    public void update(@RequestAttribute Long userId,
                       @Validated({NoticeDto.AllValidations.class}) @RequestBody NoticeDto noticeDto,
                       @PathVariable Long id)
            throws InstanceNotFoundException, NoticeUpdateStatusException {

        Notice notice = NoticeConversor.toNotice(noticeDto);

        noticeService.update(id, notice.getBody(), notice.getLocation());

    }

    @DeleteMapping("/{id}")
    public void deleteById(@RequestAttribute Long userId, @PathVariable Long id) throws InstanceNotFoundException, NoticeDeleteStatusException, NoticeUpdateStatusException {

        noticeService.deleteById(id);

    }

    @PutMapping("/{id}/status")
    public void checkNotice(@RequestAttribute Long userId,
                            @PathVariable Long id, @RequestBody NoticeStatusDto noticeStatusDto)
            throws InstanceNotFoundException, NoticeCheckStatusException {

        NoticeStatus noticeStatus;
        try {
            noticeStatus = NoticeStatus.valueOf(noticeStatusDto.getStatus().toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("noticeStatus");
        }

        noticeService.checkNotice(id, noticeStatus);

    }

    @PostMapping("/{id}/images")
    public NoticeDto addImage(@RequestAttribute Long userId, @PathVariable Long id,
                              @RequestParam("image") MultipartFile multipartFile) throws IOException, InstanceNotFoundException, ImageAlreadyUploadedException, ImageRequiredException {


        if (multipartFile.isEmpty()) {
            throw new ImageRequiredException();
        }
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());


        NoticeDto noticeDto = NoticeConversor.toNoticeDto(noticeService.addImage(id, fileName));


        String uploadDir = "public/images/" + id;
        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

        return noticeDto;
    }
}
