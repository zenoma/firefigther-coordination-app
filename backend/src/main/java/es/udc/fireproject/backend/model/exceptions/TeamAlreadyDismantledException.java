package es.udc.fireproject.backend.model.exceptions;

public class TeamAlreadyDismantledException extends Exception {
    public TeamAlreadyDismantledException(String name) {

        super("Team with code: " + name + " already dismantled.");
    }
}
