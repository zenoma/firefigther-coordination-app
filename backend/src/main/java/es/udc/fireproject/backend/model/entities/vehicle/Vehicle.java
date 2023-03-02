package es.udc.fireproject.backend.model.entities.vehicle;

import es.udc.fireproject.backend.model.entities.BaseEntity;
import es.udc.fireproject.backend.model.entities.organization.Organization;
import es.udc.fireproject.backend.model.entities.quadrant.Quadrant;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "vehicle")
public class Vehicle extends BaseEntity {

    private static final long serialVersionUID = -6662567578161123656L;

    @Column(name = "vehicle_plate")
    private String vehiclePlate;

    @Column(name = "type")
    private String type;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne(optional = false, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

    @ManyToOne(optional = false,
            fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "cuadrant_gid", nullable = false)
    private Quadrant quadrant;


    public Vehicle() {
    }

    public Vehicle(String vehiclePlate, String type, Organization organization) {
        this.vehiclePlate = vehiclePlate;
        this.type = type;
        this.organization = organization;
        this.createdAt = LocalDateTime.now();
    }

    public String getVehiclePlate() {
        return vehiclePlate;
    }

    public void setVehiclePlate(String vehiclePlate) {
        this.vehiclePlate = vehiclePlate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public Quadrant getQuadrant() {
        return quadrant;
    }

    public void setQuadrant(Quadrant quadrant) {
        this.quadrant = quadrant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return Objects.equals(vehiclePlate, vehicle.vehiclePlate) && Objects.equals(type, vehicle.type) && Objects.equals(organization, vehicle.organization) && Objects.equals(quadrant, vehicle.quadrant);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vehiclePlate, type, organization, quadrant);
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "vehiclePlate='" + vehiclePlate + '\'' +
                ", type='" + type + '\'' +
                ", createdAt=" + createdAt +
                ", organization=" + organization +
                ", cuadrant=" + quadrant +
                '}';
    }
}