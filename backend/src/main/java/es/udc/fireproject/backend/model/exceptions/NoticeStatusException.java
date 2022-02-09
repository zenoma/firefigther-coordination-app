package es.udc.fireproject.backend.model.exceptions;

public class NoticeStatusException extends Exception {

    public NoticeStatusException(Long id, String message) {
        super("Notice with id: " + id + " " + message);
    }
}
