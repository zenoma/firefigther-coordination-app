package es.udc.fireproject.backend.rest.dtos;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

public class OrganizationTypeDto {


    @Column(name = "organizationtypename")
    private String organizationTypeName;

    public OrganizationTypeDto() {
    }

    public OrganizationTypeDto(String organizationTypeName) {
        this.organizationTypeName = organizationTypeName;
    }


    @NotNull(groups = {UserDto.AllValidations.class})
    public String getOrganizationTypeName() {
        return organizationTypeName;
    }

    public void setOrganizationTypeName(String organizationTypeName) {
        this.organizationTypeName = organizationTypeName;
    }


}
