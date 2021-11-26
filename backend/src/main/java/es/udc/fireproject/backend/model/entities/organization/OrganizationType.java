package es.udc.fireproject.backend.model.entities.organization;

import javax.persistence.*;

@Entity
@Table(name = "organization_type")
public class OrganizationType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "organization_type_name")
    private String organizationTypeName;

    public OrganizationType() {
    }

    public OrganizationType(Long id, String organizationTypeName) {
        this.id = id;
        this.organizationTypeName = organizationTypeName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrganizationTypeName() {
        return organizationTypeName;
    }

    public void setOrganizationTypeName(String organizationTypeName) {
        this.organizationTypeName = organizationTypeName;
    }
}
