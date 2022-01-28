package es.udc.fireproject.backend.rest.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

public class TeamDto {

    @Id
    @NotBlank(groups = {TeamDto.AllValidations.class})
    private Long id;

    @NotBlank
    private String code;

    @NotBlank(groups = {TeamDto.AllValidations.class})
    private LocalDateTime createdAt;

    @JsonProperty("organization")
    private OrganizationDto organizationDto;

    @JsonProperty("users")
    private List<UserDto> userDtoList;


    public TeamDto(Long id, String code, LocalDateTime createdAt, OrganizationDto organizationDto, List<UserDto> userDtoList) {
        this.id = id;
        this.code = code;
        this.createdAt = createdAt;
        this.organizationDto = organizationDto;
        this.userDtoList = userDtoList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public OrganizationDto getOrganizationDto() {
        return organizationDto;
    }

    public void setOrganizationDto(OrganizationDto organizationDto) {
        this.organizationDto = organizationDto;
    }

    public List<UserDto> getUserDtoList() {
        return userDtoList;
    }

    public void setUserDtoList(List<UserDto> userDtoList) {
        this.userDtoList = userDtoList;
    }

    public interface AllValidations {
    }

    public interface UpdateValidations {
    }
}
