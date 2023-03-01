package es.udc.fireproject.backend.model.exceptions;

public class TeamAlreadyExistException extends Exception {
    public TeamAlreadyExistException(String name) {

        super("Team with code: " + name + " already exists.");
    }
}
