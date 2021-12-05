// FIXME: Redo all integration tests

//package es.udc.fireproject.backend.test.model.services;
//
//import es.udc.fireproject.backend.model.entities.user.User;
//import es.udc.fireproject.backend.model.entities.user.UserRole;
//import es.udc.fireproject.backend.model.exceptions.DuplicateInstanceException;
//import es.udc.fireproject.backend.model.exceptions.IncorrectLoginException;
//import es.udc.fireproject.backend.model.exceptions.IncorrectPasswordException;
//import es.udc.fireproject.backend.model.exceptions.InstanceNotFoundException;
//import es.udc.fireproject.backend.model.services.user.UserService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.transaction.annotation.Transactional;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertThrows;
//
//@SpringBootTest
//@ActiveProfiles("test")
//@Transactional
//public class UserServiceTest {
//
//    private final Long NON_EXISTENT_ID = Long.valueOf(-1);
//
//    @Autowired
//    private UserService userService;
//
//    private User createUser(String email) {
//        UserRole userRole = new UserRole("USER");
//        User user = new User(email,
//                "password",
//                "firstName",
//                "lastName",
//                "11111111A",
//                123456789);
//        user.setUserRole(userRole);
//        return user;
//    }
//
//
//    @Test
//    public void testSignUpAndLoginFromId() throws DuplicateInstanceException, InstanceNotFoundException {
//
//        User user = createUser("user@email.com");
//
//        userService.signUp(user);
//
//        User loggedInUser = userService.loginFromId(user.getId());
//
//        assertEquals(user, loggedInUser);
//
//    }
//
//    @Test
//    public void testSignUpDuplicatedUserName() throws DuplicateInstanceException {
//
//        User user = createUser("user@email.com");
//
//        userService.signUp(user);
//        assertThrows(DuplicateInstanceException.class, () -> userService.signUp(user));
//
//    }
//
//    @Test
//    public void testLoginFromNonExistentId() {
//        assertThrows(InstanceNotFoundException.class, () -> userService.loginFromId(NON_EXISTENT_ID));
//    }
//
//    @Test
//    public void testLogin() throws DuplicateInstanceException, IncorrectLoginException {
//
//        User user = createUser("user@email.com");
//        String clearPassword = user.getPassword();
//
//        userService.signUp(user);
//
//        User loggedInUser = userService.login(user.getEmail(), clearPassword);
//
//        assertEquals(user, loggedInUser);
//
//    }
//
//    @Test
//    public void testLoginWithIncorrectPassword() throws DuplicateInstanceException {
//
//        User user = createUser("user@email.com");
//        String clearPassword = user.getPassword();
//
//        userService.signUp(user);
//        assertThrows(IncorrectLoginException.class, () ->
//                userService.login(user.getEmail(), 'X' + clearPassword));
//
//    }
//
//    @Test
//    public void testLoginWithNonExistentUserName() {
//        assertThrows(IncorrectLoginException.class, () -> userService.login("X", "Y"));
//    }
//
//    @Test
//    public void testUpdateProfile() throws InstanceNotFoundException, DuplicateInstanceException {
//
//        User user = createUser("user@email.com");
//
//        userService.signUp(user);
//
//        user.setFirstName('X' + user.getFirstName());
//        user.setLastName('X' + user.getLastName());
//        user.setEmail('X' + user.getEmail());
//
//        userService.updateProfile(user.getId(), 'X' + user.getFirstName(), 'X' + user.getLastName(),
//                'X' + user.getEmail());
//
//        User updatedUser = userService.loginFromId(user.getId());
//
//        assertEquals(user, updatedUser);
//
//    }
//
//    @Test
//    public void testUpdateProfileWithNonExistentId() {
//        assertThrows(InstanceNotFoundException.class, () ->
//                userService.updateProfile(NON_EXISTENT_ID, "X", "X", "X"));
//    }
//
//    @Test
//    public void testChangePassword() throws DuplicateInstanceException, InstanceNotFoundException,
//            IncorrectPasswordException, IncorrectLoginException {
//
//        User user = createUser("user@email.com");
//        String oldPassword = user.getPassword();
//        String newPassword = 'X' + oldPassword;
//
//        userService.signUp(user);
//        userService.changePassword(user.getId(), oldPassword, newPassword);
//        userService.login(user.getEmail(), newPassword);
//
//    }
//
//    @Test
//    public void testChangePasswordWithNonExistentId() {
//        assertThrows(InstanceNotFoundException.class, () ->
//                userService.changePassword(NON_EXISTENT_ID, "X", "Y"));
//    }
//
//    @Test
//    public void testChangePasswordWithIncorrectPassword() throws DuplicateInstanceException {
//
//        User user = createUser("user@email.com");
//        String oldPassword = user.getPassword();
//        String newPassword = 'X' + oldPassword;
//
//        userService.signUp(user);
//        assertThrows(IncorrectPasswordException.class, () ->
//                userService.changePassword(user.getId(), 'Y' + oldPassword, newPassword));
//
//    }
//
//}
