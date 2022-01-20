package es.udc.fireproject.backend.model.exceptions;


public class PermissionException extends Exception {


    public PermissionException(String message, Long id) {
        super(message + " target ID:" + id);
    }
}
