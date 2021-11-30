package es.udc.fireproject.backend.model.services.user;

import es.udc.fireproject.backend.model.entities.user.User;
import es.udc.fireproject.backend.model.exceptions.InstanceNotFoundException;

public interface PermissionChecker {

    void checkUserExists(Long userId) throws InstanceNotFoundException;

    User checkUser(Long userId) throws InstanceNotFoundException;

}
