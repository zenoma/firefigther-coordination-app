package es.udc.fireproject.backend.rest.controllers;

import es.udc.fireproject.backend.model.entities.team.Team;
import es.udc.fireproject.backend.model.entities.user.User;
import es.udc.fireproject.backend.model.exceptions.InstanceNotFoundException;
import es.udc.fireproject.backend.model.services.team.TeamService;
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
    TeamService teamService;


    @PostMapping("/create")
    public TeamDto create(@RequestAttribute Long userId,
                          @Validated({UserDto.AllValidations.class})
                          @RequestBody Map<String, String> jsonParams)
            throws InstanceNotFoundException {

        Team team = teamService.create(jsonParams.get("code"), Long.valueOf(jsonParams.get("organizationId")));
        return TeamConversor.toTeamDto(team);

    }

    @GetMapping("")
    public List<TeamDto> findAll(@RequestAttribute Long userId,
                                 @RequestParam(required = false) String code,
                                 @RequestParam(required = false) Long organizationId) {

        List<TeamDto> teamDtoList = new ArrayList<>();


        if (code != null) {
            for (Team team : teamService.findByCode(code)) {
                teamDtoList.add(TeamConversor.toTeamDto(team));
            }
        } else if (organizationId != null) {
            for (Team team : teamService.findByOrganizationId(organizationId)) {
                teamDtoList.add(TeamConversor.toTeamDto(team));
            }
        } else {
            for (Team team : teamService.findByCode("")) {
                teamDtoList.add(TeamConversor.toTeamDto(team));
            }
        }

        return teamDtoList;
    }

    @GetMapping("/{id}")
    public TeamDto findById(@RequestAttribute Long userId, @PathVariable Long id)
            throws InstanceNotFoundException {
        return TeamConversor.toTeamDto(teamService.findById(id));
    }

    @GetMapping("/myTeam")
    public TeamDto findMyTeam(@RequestAttribute Long userId)
            throws InstanceNotFoundException {
        return TeamConversor.toTeamDto(teamService.findByUserId(userId));
    }

    @PutMapping("/{id}/addUser/{memberId}")
    public void addUser(@RequestAttribute Long userId, @PathVariable Long id, @PathVariable Long memberId)
            throws InstanceNotFoundException {
        teamService.addMember(id, memberId);
    }


    @DeleteMapping("/{id}")
    public void delete(@RequestAttribute Long userId, @PathVariable Long id)
            throws InstanceNotFoundException {
        teamService.deleteById(id);
    }

    @PutMapping("/{id}/deleteUser/{memberId}")
    public void deleteUser(@RequestAttribute Long userId, @PathVariable Long id, @PathVariable Long memberId)
            throws InstanceNotFoundException {
        teamService.deleteMember(id, memberId);
    }

    @GetMapping("/{id}/listUsers")
    public List<UserDto> findAllUsers(@RequestAttribute Long userId, @PathVariable Long id)
            throws InstanceNotFoundException {
        List<UserDto> userDtoList = new ArrayList<>();
        for (User user : teamService.findAllUsers(id)) {
            userDtoList.add(UserConversor.toUserDto(user));
        }
        return userDtoList;
    }

    @PutMapping("/{id}")
    public void update(@RequestAttribute Long userId, @PathVariable Long id, @RequestBody TeamDto teamDto)
            throws InstanceNotFoundException {
        teamService.update(id, teamDto.getCode());
    }


}
