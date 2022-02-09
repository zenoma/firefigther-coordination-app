package es.udc.fireproject.backend.model.exceptions;


public class DuplicatedInstanceException extends InstanceException {

    public DuplicatedInstanceException(String name, Object key) {
        super(name, key);
    }

}
