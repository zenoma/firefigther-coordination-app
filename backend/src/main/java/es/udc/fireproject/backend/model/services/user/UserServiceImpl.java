package es.udc.fireproject.backend.model.services.user;

import es.udc.fireproject.backend.model.entities.user.User;
import es.udc.fireproject.backend.model.entities.user.UserRepository;
import es.udc.fireproject.backend.model.entities.user.UserRole;
import es.udc.fireproject.backend.model.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private static final String USER_NOT_FOUND = "User not found";
    private static final String TARGET_USER_NOT_FOUND = "Target user not found";

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void signUp(User user) throws DuplicateInstanceException {

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new DuplicateInstanceException("project.entities.user", user.getEmail());
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        user.setUserRole(UserRole.USER);
        userRepository.save(user);

    }

    @Override
    @Transactional(readOnly = true)
    public User login(String email, String password) throws IncorrectLoginException {

        User user = userRepository.findByEmail(email).orElseThrow(() -> new IncorrectLoginException(email, password));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IncorrectLoginException(email, password);
        }

        return user;

    }

    @Override
    @Transactional(readOnly = true)
    public User loginFromId(Long id) throws InstanceNotFoundException {

        return userRepository.findById(id).orElseThrow(() -> new InstanceNotFoundException(USER_NOT_FOUND, id));
    }

    @Override
    public User updateProfile(Long id, String firstName, String lastName, String email, Integer phoneNumber, String dni) throws InstanceNotFoundException {

        User user = userRepository.findById(id).orElseThrow(() -> new InstanceNotFoundException(USER_NOT_FOUND, id));

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setDni(dni);

        userRepository.save(user);
        return user;

    }

    @Override
    public void changePassword(Long id, String oldPassword, String newPassword)
            throws InstanceNotFoundException, IncorrectPasswordException {

        User user = userRepository.findById(id).orElseThrow(() -> new InstanceNotFoundException(USER_NOT_FOUND, id));


        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new IncorrectPasswordException();
        } else {
            user.setPassword(passwordEncoder.encode(newPassword));
        }

        userRepository.save(user);

    }

    @Override
    public void updateRole(Long id, Long targetId, UserRole userRole) throws InstanceNotFoundException,
            InsufficientRolePermissionException {
        User user = userRepository.findById(id).orElseThrow(() -> new InstanceNotFoundException(USER_NOT_FOUND, id));

        User targetUser = userRepository.findById(targetId).orElseThrow(
                () -> new InstanceNotFoundException(TARGET_USER_NOT_FOUND, targetId));


        if (user.getUserRole().isHigherThan(targetUser.getUserRole()) || userRole.isLowerThan(user.getUserRole())) {
            throw new InsufficientRolePermissionException(id, targetId);
        }

        targetUser.setUserRole(userRole);

        userRepository.save(targetUser);

    }
}
