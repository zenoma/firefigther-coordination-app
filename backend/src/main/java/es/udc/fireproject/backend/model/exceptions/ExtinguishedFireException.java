package es.udc.fireproject.backend.model.exceptions;

public class ExtinguishedFireException extends Exception {

    public ExtinguishedFireException(Long id, String message) {
        super("Fire with id: " + id + " " + message);
    }
}
