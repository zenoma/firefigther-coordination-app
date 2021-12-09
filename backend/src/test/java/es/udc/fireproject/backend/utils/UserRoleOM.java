package es.udc.fireproject.backend.utils;

import es.udc.fireproject.backend.model.entities.user.UserRole;

public class UserRoleOM {

    public static UserRole withDefaultValues() {
        return new UserRole("USER");
    }

    public static UserRole withInvalidValues() {
        return new UserRole("");
    }
}
