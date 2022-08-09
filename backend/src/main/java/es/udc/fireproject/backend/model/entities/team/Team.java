package es.udc.fireproject.backend.model.entities.team;

import es.udc.fireproject.backend.model.entities.BaseEntity;
import es.udc.fireproject.backend.model.entities.organization.Organization;
import es.udc.fireproject.backend.model.entities.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
public class Team extends BaseEntity {

    private static final long serialVersionUID = -6662567578161123656L;

    @Column(name = "code")
    @NotBlank
    private String code;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne(optional = false, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

    @OneToMany(
            mappedBy = "team",
            fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<User> userList;

    public Team() {

    }

    public Team(String code, Organization organization) {
        this.code = code;
        this.organization = organization;
        this.createdAt = LocalDateTime.now();
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return Objects.equals(code, team.code) && Objects.equals(organization, team.organization);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, organization);
    }

    @Override
    public String toString() {
        return "Team{" +
                "id=" + getId() +
                ", code='" + code + '\'' +
                ", createdAt=" + createdAt +
                ", organization=" + organization +
                ", userList=" + userList +
                '}';
    }
}
