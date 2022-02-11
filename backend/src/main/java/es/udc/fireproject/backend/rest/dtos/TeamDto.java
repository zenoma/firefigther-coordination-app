package es.udc.fireproject.backend.rest.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class TeamDto extends BaseDto {

    private static final long serialVersionUID = -9141625918440183253L;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeamDto teamDto = (TeamDto) o;
        return Objects.equals(id, teamDto.id) && Objects.equals(code, teamDto.code) && Objects.equals(createdAt, teamDto.createdAt) && Objects.equals(organizationDto, teamDto.organizationDto) && Objects.equals(userDtoList, teamDto.userDtoList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, createdAt, organizationDto, userDtoList);
    }

    @Override
    public String toString() {
        return "TeamDto{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", createdAt=" + createdAt +
                ", organizationDto=" + organizationDto +
                ", userDtoList=" + userDtoList +
                '}';
    }

    public interface AllValidations {
    }

    public interface UpdateValidations {
    }
}
