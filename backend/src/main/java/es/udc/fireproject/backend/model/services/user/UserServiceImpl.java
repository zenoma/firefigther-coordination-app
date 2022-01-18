package es.udc.fireproject.backend.model.services.user;

import es.udc.fireproject.backend.model.entities.user.User;
import es.udc.fireproject.backend.model.entities.user.UserRepository;
import es.udc.fireproject.backend.model.entities.user.UserRole;
import es.udc.fireproject.backend.model.exceptions.DuplicateInstanceException;
import es.udc.fireproject.backend.model.exceptions.IncorrectLoginException;
import es.udc.fireproject.backend.model.exceptions.IncorrectPasswordException;
import es.udc.fireproject.backend.model.exceptions.InstanceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private static final String USER_NOT_FOUND = "User not found";

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

        Optional<User> user = userRepository.findByEmail(email);

        if (user.isEmpty()) {
            throw new IncorrectLoginException(email, password);
        }

        if (!passwordEncoder.matches(password, user.get().getPassword())) {
            throw new IncorrectLoginException(email, password);
        }

        return user.get();

    }

    @Override
    @Transactional(readOnly = true)
    public User loginFromId(Long id) throws InstanceNotFoundException {

        Optional<User> userOpt = userRepository.findById(id);

        if (userOpt.isEmpty()) {
            throw new InstanceNotFoundException(USER_NOT_FOUND, id);
        } else {
            return userOpt.get();
        }
    }

    @Override
    public User updateProfile(Long id, String firstName, String lastName, String email, Integer phoneNumber, String dni) throws InstanceNotFoundException {

        Optional<User> userOpt = userRepository.findById(id);

        if (userOpt.isEmpty()) {
            throw new InstanceNotFoundException(USER_NOT_FOUND, id);
        } else {
            User user = userOpt.get();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            user.setPhoneNumber(phoneNumber);
            user.setDni(dni);

            userRepository.save(user);
            return user;
        }

    }

    @Override
    public void changePassword(Long id, String oldPassword, String newPassword)
            throws InstanceNotFoundException, IncorrectPasswordException {

        Optional<User> userOpt = userRepository.findById(id);

        if (userOpt.isEmpty()) {
            throw new InstanceNotFoundException(USER_NOT_FOUND, id);
        } else {
            User user = userOpt.get();

            if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
                throw new IncorrectPasswordException();
            } else {
                user.setPassword(passwordEncoder.encode(newPassword));
            }

            userRepository.save(user);
        }
    }

    @Override
    public void updateRole(Long id, UserRole userRole) throws InstanceNotFoundException {
        Optional<User> userOpt = userRepository.findById(id);

        if (userOpt.isEmpty()) {
            throw new InstanceNotFoundException(USER_NOT_FOUND, id);
        } else {
            User user = userOpt.get();
            user.setUserRole(userRole);

            userRepository.save(user);

        }
    }
}
