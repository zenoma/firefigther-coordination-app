package es.udc.fireproject.backend.rest.controllers;

import es.udc.fireproject.backend.model.entities.team.Team;
import es.udc.fireproject.backend.model.entities.user.User;
import es.udc.fireproject.backend.model.exceptions.InstanceNotFoundException;
import es.udc.fireproject.backend.model.exceptions.TeamAlreadyDismantledException;
import es.udc.fireproject.backend.model.exceptions.TeamAlreadyExistException;
import es.udc.fireproject.backend.model.services.firemanagement.FireManagementService;
import es.udc.fireproject.backend.model.services.personalmanagement.PersonalManagementService;
import es.udc.fireproject.backend.rest.dtos.TeamDto;
import es.udc.fireproject.backend.rest.dtos.UserDto;
import es.udc.fireproject.backend.rest.dtos.conversors.TeamConversor;
import es.udc.fireproject.backend.rest.dtos.conversors.UserConversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/teams")
public class TeamController {

    @Autowired
    PersonalManagementService personalManagementService;

    @Autowired
    FireManagementService fireManagementService;


    @PostMapping("")
    public TeamDto create(@RequestAttribute Long userId,
                          @Validated({UserDto.AllValidations.class})
                          @RequestBody Map<String, String> jsonParams)
            throws InstanceNotFoundException, TeamAlreadyExistException {

        Team team = personalManagementService.createTeam(jsonParams.get("code"), Long.valueOf(jsonParams.get("organizationId")));
        return TeamConversor.toTeamDto(team);

    }

    @GetMapping("")
    public List<TeamDto> findAll(@RequestAttribute Long userId,
                                 @RequestParam(required = false) String code,
                                 @RequestParam(required = false) Long organizationId) {

        List<TeamDto> teamDtoList = new ArrayList<>();


        if (code != null) {
            for (Team team : personalManagementService.findTeamByCode(code)) {
                teamDtoList.add(TeamConversor.toTeamDto(team));
            }
        } else if (organizationId != null) {
            for (Team team : personalManagementService.findTeamsByOrganizationId(organizationId)) {
                teamDtoList.add(TeamConversor.toTeamDto(team));
            }
        } else {
            for (Team team : personalManagementService.findTeamByCode("")) {
                teamDtoList.add(TeamConversor.toTeamDto(team));
            }
        }

        return teamDtoList;
    }

    @GetMapping("/{id}")
    public TeamDto findById(@RequestAttribute Long userId, @PathVariable Long id)
            throws InstanceNotFoundException {
        return TeamConversor.toTeamDto(personalManagementService.findTeamById(id));
    }

    @GetMapping("/myTeam")
    public TeamDto findMyTeam(@RequestAttribute Long userId)
            throws InstanceNotFoundException {
        return TeamConversor.toTeamDto(personalManagementService.findTeamByUserId(userId));
    }

    @PostMapping("/{id}/addUser/")
    public void addUser(@RequestAttribute Long userId, @PathVariable Long id, @RequestBody Map<String, String> jsonParams)
            throws InstanceNotFoundException, TeamAlreadyDismantledException {
        personalManagementService.addMember(id, Long.valueOf(jsonParams.get("memberId")));
    }


    @DeleteMapping("/{id}")
    public void delete(@RequestAttribute Long userId, @PathVariable Long id)
            throws InstanceNotFoundException, TeamAlreadyDismantledException {
        personalManagementService.dismantleTeamById(id);
    }

    @PostMapping("/{id}/deleteUser")
    public void deleteUser(@RequestAttribute Long userId, @PathVariable Long id, @RequestBody Map<String, String> jsonParams)
            throws InstanceNotFoundException, TeamAlreadyDismantledException {
        personalManagementService.deleteMember(id, Long.valueOf(jsonParams.get("memberId")));
    }

    @GetMapping("/{id}/users")
    public List<UserDto> findAllUsers(@RequestAttribute Long userId, @PathVariable Long id)
            throws InstanceNotFoundException {
        List<UserDto> userDtoList = new ArrayList<>();
        for (User user : personalManagementService.findAllUsersByTeamId(id)) {
            userDtoList.add(UserConversor.toUserDto(user));
        }
        return userDtoList;
    }

    @PutMapping("/{id}")
    public void update(@RequestAttribute Long userId, @PathVariable Long id, @RequestBody TeamDto teamDto)
            throws InstanceNotFoundException, TeamAlreadyDismantledException {
        personalManagementService.updateTeam(id, teamDto.getCode());
    }


    @PostMapping("/{id}/deploy")
    public TeamDto deploy(@RequestAttribute Long userId, @PathVariable Long id, @RequestBody Map<String, String> jsonParams)
            throws InstanceNotFoundException, TeamAlreadyDismantledException {

        return TeamConversor.toTeamDto(fireManagementService.deployTeam(id, Integer.valueOf(jsonParams.get("gid"))));
    }

    @PostMapping("/{id}/retract")
    public TeamDto retract(@RequestAttribute Long userId, @PathVariable Long id)
            throws InstanceNotFoundException, TeamAlreadyDismantledException {

        return TeamConversor.toTeamDto(fireManagementService.retractTeam(id));

    }

}
