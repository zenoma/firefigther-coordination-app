package es.udc.fireproject.backend.model.entities.organization;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "organization_type")
public class OrganizationType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    public OrganizationType() {
    }

    public OrganizationType(String name) {
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
}
