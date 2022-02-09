package es.udc.fireproject.backend.model.entities.organization;

import es.udc.fireproject.backend.model.entities.BaseObject;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Entity
@Table(name = "organization_type")
public class OrganizationType extends BaseObject {

    private static final long serialVersionUID = 3441744938370182772L;


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

    public OrganizationType(Long id, String name) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrganizationType that = (OrganizationType) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "OrganizationType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
