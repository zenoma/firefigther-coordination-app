package es.udc.fireproject.backend.model.exceptions;

public class ImageAlreadyUploadedException extends Exception {
    public ImageAlreadyUploadedException(Long id, String message) {

        super("Image with id: " + id + " " + message);
    }
}
