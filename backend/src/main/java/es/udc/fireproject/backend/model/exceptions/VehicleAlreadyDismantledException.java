package es.udc.fireproject.backend.model.exceptions;

public class VehicleAlreadyDismantledException extends Exception {
    public VehicleAlreadyDismantledException(String name) {

        super("Vehicle with plate: " + name + " already dismantled.");
    }
}
