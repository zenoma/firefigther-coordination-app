package es.udc.fireproject.backend.rest.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class OrganizationTypeDto {

    @NotNull(groups = {OrganizationTypeDto.AllValidations.class})
    private Long id;

    @NotBlank
    @JsonProperty("name")
    private String name;

    public OrganizationTypeDto() {
    }

    public OrganizationTypeDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public interface AllValidations {
    }

    public interface UpdateValidations {
    }

}
