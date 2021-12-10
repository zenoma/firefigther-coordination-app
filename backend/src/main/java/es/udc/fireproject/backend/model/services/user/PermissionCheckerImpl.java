package es.udc.fireproject.backend.model.services.user;

import es.udc.fireproject.backend.model.entities.user.User;
import es.udc.fireproject.backend.model.entities.user.UserRepository;
import es.udc.fireproject.backend.model.exceptions.InstanceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PermissionCheckerImpl implements PermissionChecker {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void checkUserExists(Long userId) throws InstanceNotFoundException {

        if (!userRepository.existsById(userId)) {
            throw new InstanceNotFoundException("project.entities.user", userId);
        }

    }

    @Override
    public User checkUser(Long userId) throws InstanceNotFoundException {

        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty()) {
            throw new InstanceNotFoundException("project.entities.user", userId);
        }

        return user.get();

    }

}
