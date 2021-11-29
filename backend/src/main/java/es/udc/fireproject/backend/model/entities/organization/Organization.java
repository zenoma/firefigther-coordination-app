package es.udc.fireproject.backend.model.entities.organization;

import org.locationtech.jts.geom.Geometry;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String code;

    @NotBlank
    private String name;

    @NotBlank
    @Column(name = "headquarters_address")
    private String headquartersAddress;

    @Transient
    private Geometry location;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "organization_type_id")
    private OrganizationType organizationType;

    public Organization() {
    }

    public Organization(String code, String name, String headquartersAddress, Geometry location, OrganizationType organizationType) {
        this.code = code;
        this.name = name;
        this.headquartersAddress = headquartersAddress;
        this.location = location;
        this.organizationType = organizationType;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadquartersAddress() {
        return headquartersAddress;
    }

    public void setHeadquartersAddress(String headquartersAddress) {
        this.headquartersAddress = headquartersAddress;
    }

    public Geometry getLocation() {
        return location;
    }

    public void setLocation(Geometry location) {
        this.location = location;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public OrganizationType getOrganizationType() {
        return organizationType;
    }

    public void setOrganizationType(OrganizationType organizationType) {
        this.organizationType = organizationType;
    }
}
