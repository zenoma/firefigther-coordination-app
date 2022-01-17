package es.udc.fireproject.backend.model.services.user;

import es.udc.fireproject.backend.model.entities.user.User;
import es.udc.fireproject.backend.model.entities.user.UserRole;
import es.udc.fireproject.backend.model.exceptions.DuplicateInstanceException;
import es.udc.fireproject.backend.model.exceptions.IncorrectLoginException;
import es.udc.fireproject.backend.model.exceptions.IncorrectPasswordException;
import es.udc.fireproject.backend.model.exceptions.InstanceNotFoundException;

public interface UserService {

    void signUp(User user) throws DuplicateInstanceException;

    User login(String email, String password) throws IncorrectLoginException;

    User loginFromId(Long id) throws InstanceNotFoundException;

    User updateProfile(Long id, String firstName, String lastName, String email, Integer phoneNumber, String dni) throws InstanceNotFoundException;

    void changePassword(Long id, String oldPassword, String newPassword)
            throws InstanceNotFoundException, IncorrectPasswordException;

    void updateRole(Long id, UserRole userRole) throws InstanceNotFoundException;
}
