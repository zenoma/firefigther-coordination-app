package es.udc.fireproject.backend.rest.dtos;

import es.udc.fireproject.backend.model.entities.organization.OrganizationType;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.util.List;

public class OrganizationTypeDto {


    @Column(name = "organizationtypename")
    @NotNull(groups = {OrganizationTypeDto.AllValidations.class})
    private List<OrganizationType> organizationTypeList;

    public OrganizationTypeDto() {
    }

    public OrganizationTypeDto(List<OrganizationType> organizationTypeName) {
        this.organizationTypeList = organizationTypeName;
    }


    public List<OrganizationType> getOrganizationTypeName() {
        return organizationTypeList;
    }

    public void setOrganizationTypeName(List<OrganizationType> organizationTypeName) {
        this.organizationTypeList = organizationTypeName;
    }

    public interface AllValidations {
    }

    public interface UpdateValidations {
    }

}
