package es.udc.fireproject.backend.model.entities.user;

public enum UserRole {
    // Highest rol functions will have the bigger number
    COORDINATOR(2), MANAGER(1), USER(0);

    public final Integer priority;

    UserRole(Integer priority) {
        this.priority = priority;
    }
}
