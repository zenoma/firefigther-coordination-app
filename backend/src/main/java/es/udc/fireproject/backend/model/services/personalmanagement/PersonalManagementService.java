package es.udc.fireproject.backend.model.services.personalmanagement;

import es.udc.fireproject.backend.model.entities.organization.Organization;
import es.udc.fireproject.backend.model.entities.organization.OrganizationType;
import es.udc.fireproject.backend.model.entities.team.Team;
import es.udc.fireproject.backend.model.entities.user.User;
import es.udc.fireproject.backend.model.entities.user.UserRole;
import es.udc.fireproject.backend.model.entities.vehicle.Vehicle;
import es.udc.fireproject.backend.model.exceptions.*;
import org.locationtech.jts.geom.Point;

import java.util.List;

public interface PersonalManagementService {

    // ORGANIZATION SERVICES
    OrganizationType createOrganizationType(String name);

    OrganizationType findOrganizationTypeById(Long id) throws InstanceNotFoundException;

    List<OrganizationType> findAllOrganizationTypes();

    List<Organization> findOrganizationByNameOrCode(String nameOrCode);

    Organization findOrganizationById(Long id) throws InstanceNotFoundException;

    List<Organization> findOrganizationByOrganizationTypeName(String organizationTypeName);

    List<Organization> findAllOrganizations();

    Organization createOrganization(String code, String name, String headquartersAddress, Point location, String organizationTypeName);

    Organization createOrganization(Organization organization);

    void deleteOrganizationById(Long id);

    Organization updateOrganization(Long id, String name, String code, String headquartersAddress, Point location) throws InstanceNotFoundException;

    // TEAM SERVICES

    List<Team> findTeamByCode(String code);

    Team createTeam(String code, Long organizationId) throws InstanceNotFoundException;

    void deleteTeamById(Long id) throws InstanceNotFoundException;

    Team updateTeam(Long id, String code) throws InstanceNotFoundException;

    Team addMember(Long teamId, Long userId) throws InstanceNotFoundException;

    void deleteMember(Long teamId, Long userId) throws InstanceNotFoundException;

    List<User> findAllUsersByTeamId(Long teamId) throws InstanceNotFoundException;

    Team findTeamById(Long teamId) throws InstanceNotFoundException;

    Team findTeamByUserId(Long userId) throws InstanceNotFoundException;

    List<Team> findTeamByOrganizationId(Long organizationId);


    // VEHICLE SERVICES

    Vehicle createVehicle(String vehiclePlate, String type, Long organizationId) throws InstanceNotFoundException;

    void deleteVehicleById(Long id) throws InstanceNotFoundException;

    Vehicle updateVehicle(Long id, String vehiclePlate, String type) throws InstanceNotFoundException;

    Vehicle findVehicleById(Long teamId) throws InstanceNotFoundException;

    List<Vehicle> findVehiclesByOrganizationId(Long organizationId);

    List<Vehicle> findAllVehicles();

    // USER SERVICES

    List<User> findAllUsers();

    void signUp(User user) throws DuplicateInstanceException;

    User login(String email, String password) throws IncorrectLoginException;

    User loginFromId(Long id) throws InstanceNotFoundException;

    User updateProfile(Long id, String firstName, String lastName, String email, Integer phoneNumber, String dni) throws InstanceNotFoundException;

    void changePassword(Long id, String oldPassword, String newPassword)
            throws InstanceNotFoundException, IncorrectPasswordException;

    void updateRole(Long id, Long targetId, UserRole userRole) throws InstanceNotFoundException,
            InsufficientRolePermissionException;


}
