package es.udc.fireproject.backend.integration.services;

import es.udc.fireproject.backend.model.entities.user.User;
import es.udc.fireproject.backend.model.entities.user.UserRole;
import es.udc.fireproject.backend.model.exceptions.*;
import es.udc.fireproject.backend.model.services.user.UserService;
import es.udc.fireproject.backend.utils.UserOM;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class UserServiceImplTest {

    private final Long INVALID_USER_ID = -1L;

    @Autowired
    private UserService userService;

    @Test
    void givenValidData_whenSignUpAndLoginFromId_thenUserIsFound() throws DuplicateInstanceException, InstanceNotFoundException {

        User user = UserOM.withDefaultValues();

        userService.signUp(user);

        User loggedInUser = userService.loginFromId(user.getId());

        Assertions.assertEquals(user, loggedInUser, "Users must be the same");

    }

    @Test
    void givenDuplicatedData_whenSignUp_thenDuplicateInstanceException() throws DuplicateInstanceException {
        User user = UserOM.withDefaultValues();

        userService.signUp(user);
        Assertions.assertThrows(DuplicateInstanceException.class, () -> userService.signUp(user), "DuplicateInstanceException expected");

    }

    @Test
    void givenInvalidData_whenLoginFromId_thenInstanceNotFoundException() {
        Assertions.assertThrows(InstanceNotFoundException.class, () -> userService.loginFromId(INVALID_USER_ID));
    }

    @Test
    void givenValidData_whenLogin_thenUserLoggedSuccessfully() throws DuplicateInstanceException, IncorrectLoginException {

        User user = UserOM.withDefaultValues();


        String clearPassword = user.getPassword();

        userService.signUp(user);

        User loggedInUser = userService.login(user.getEmail(), clearPassword);

        Assertions.assertEquals(user, loggedInUser, "Users must be the same");

    }

    @Test
    void givenInvalidPassword_whenLogin_thenIncorrectLoginException() throws DuplicateInstanceException {

        User user = UserOM.withDefaultValues();

        String clearPassword = user.getPassword();

        userService.signUp(user);
        Assertions.assertThrows(IncorrectLoginException.class, () ->
                userService.login(user.getEmail(), 'X' + clearPassword), " Password must be incorrect");

    }

    @Test
    void givenInvalidEmail_whenLogin_thenIncorrectLoginException() {
        Assertions.assertThrows(IncorrectLoginException.class, () -> userService.login("X", "Y"), " User must not exist");
    }


    @Test
    void givenValidData_whenUpdateProfile_thenUserUpdatedSuccessfully() throws InstanceNotFoundException, DuplicateInstanceException {

        User user = UserOM.withDefaultValues();

        userService.signUp(user);

        user.setFirstName('X' + user.getFirstName());
        user.setLastName('X' + user.getLastName());
        user.setEmail('X' + user.getEmail());

        userService.updateProfile(user.getId(), 'X' + user.getFirstName(), 'X' + user.getLastName(),
                'X' + user.getEmail(), 111111111, "11111111S");

        User updatedUser = userService.loginFromId(user.getId());

        Assertions.assertEquals(user, updatedUser, "User must be updated");

    }


    @Test
    void givenInvalidData_whenUpdateProfile_thenInstanceNotFoundException() {
        Assertions.assertThrows(InstanceNotFoundException.class, () ->
                userService.updateProfile(INVALID_USER_ID, "X", "X", "X", 111111111, "11111111S"), "User not existent");
    }

    @Test
    void givenValidData_whenChangePassword_thenPasswordSuccessfullyChanged() throws DuplicateInstanceException, InstanceNotFoundException,
            IncorrectPasswordException {

        User user = UserOM.withDefaultValues();


        String oldPassword = user.getPassword();
        String newPassword = 'X' + oldPassword;
        userService.signUp(user);

        userService.changePassword(user.getId(), oldPassword, newPassword);

        Assertions.assertDoesNotThrow(() -> userService.login(user.getEmail(), newPassword));
    }


    @Test
    void givenInvalidID_whenChangePassword_thenInstanceNotFoundException() {
        Assertions.assertThrows(InstanceNotFoundException.class, () ->
                userService.changePassword(INVALID_USER_ID, "X", "Y"), "User non existent");
    }


    @Test
    void givenIncorrectPassword_whenChangePassword_thenInstanceNotFoundException() throws DuplicateInstanceException {
        User user = UserOM.withDefaultValues();

        String oldPassword = user.getPassword();
        String newPassword = 'X' + oldPassword;

        userService.signUp(user);
        Assertions.assertThrows(IncorrectPasswordException.class, () ->
                userService.changePassword(user.getId(), 'Y' + oldPassword, newPassword), "IncorrectPassword Exception expected");

    }


    @Test
    void givenValidData_whenSignUp_thenUserHasUserRole() throws DuplicateInstanceException {
        User user = UserOM.withDefaultValues();

        userService.signUp(user);

        Assertions.assertEquals(UserRole.USER, user.getUserRole(), "Role must be USER");

    }

    @Test
    void giveUsersWithHigherRole_whenUpdateLowerRole_thenUpdateRolSuccessfully() throws DuplicateInstanceException,
            InstanceNotFoundException, PermissionException {
        int totalUsers = 2;
        List<User> userList = UserOM.withRandomNames(totalUsers);
        User user = userList.get(0);
        User targetUser = userList.get(1);
        userService.signUp(user);
        user.setUserRole(UserRole.COORDINATOR);
        userService.signUp(targetUser);
        targetUser.setUserRole(UserRole.MANAGER);

        userService.updateRole(user.getId(), targetUser.getId(), UserRole.USER);

        Assertions.assertEquals(UserRole.USER, targetUser.getUserRole(), "Role must be MANAGER");

    }

    @Test
    void giveUsersWithHigherRole_whenUpdateHigherRole_thenUpdateRolSuccessfully() throws DuplicateInstanceException,
            InstanceNotFoundException, PermissionException {
        int totalUsers = 2;
        List<User> userList = UserOM.withRandomNames(totalUsers);
        User user = userList.get(0);
        User targetUser = userList.get(1);
        userService.signUp(user);
        user.setUserRole(UserRole.COORDINATOR);
        userService.signUp(targetUser);
        targetUser.setUserRole(UserRole.MANAGER);

        userService.updateRole(user.getId(), targetUser.getId(), UserRole.COORDINATOR);

        Assertions.assertEquals(UserRole.COORDINATOR, targetUser.getUserRole(), "Role must be MANAGER");

    }

    @Test
    void giveUserWithLessRole_whenUpdateRole_thenPermissionException() throws DuplicateInstanceException {
        int totalUsers = 2;
        List<User> userList = UserOM.withRandomNames(totalUsers);
        User user = userList.get(0);
        User targetUser = userList.get(1);
        userService.signUp(user);
        userService.signUp(targetUser);

        Assertions.assertThrows(PermissionException.class,
                () -> userService.updateRole(user.getId(), targetUser.getId(), UserRole.MANAGER),
                "User has not enought permission");

    }

    @Test
    void giveUsersWithSameRole_whenUpdateLowerRole_thenUpdateRolSuccessfully() throws DuplicateInstanceException,
            InstanceNotFoundException, PermissionException {
        int totalUsers = 2;
        List<User> userList = UserOM.withRandomNames(totalUsers);
        User user = userList.get(0);
        User targetUser = userList.get(1);
        user.setUserRole(UserRole.MANAGER);
        targetUser.setUserRole(UserRole.MANAGER);
        userService.signUp(user);
        userService.signUp(targetUser);

        userService.updateRole(user.getId(), targetUser.getId(), UserRole.USER);

        Assertions.assertEquals(UserRole.USER, targetUser.getUserRole(),
                "Updated user role must be USER");

    }

    @Test
    void giveUsersWithSameRole_whenUpdateHigherRole_thenPermissionException() throws DuplicateInstanceException {
        int totalUsers = 2;
        List<User> userList = UserOM.withRandomNames(totalUsers);
        User user = userList.get(0);
        User targetUser = userList.get(1);
        user.setUserRole(UserRole.MANAGER);
        targetUser.setUserRole(UserRole.MANAGER);
        userService.signUp(user);
        userService.signUp(targetUser);

        Assertions.assertThrows(PermissionException.class,
                () -> userService.updateRole(user.getId(), targetUser.getId(), UserRole.COORDINATOR),
                "User has not enough permission");

    }
}
