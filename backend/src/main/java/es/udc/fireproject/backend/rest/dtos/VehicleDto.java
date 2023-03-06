package es.udc.fireproject.backend.rest.dtos;

import java.time.LocalDateTime;
import java.util.Objects;

public class VehicleDto extends BaseDto {

    private static final long serialVersionUID = -6531781678159378396L;

    private Long id;
    private String vehiclePlate;
    private String type;
    private LocalDateTime createdAt;
    private OrganizationDto organization;
    private QuadrantInfoDto quadrant;

    public VehicleDto(Long id, String vehiclePlate, String type, LocalDateTime createdAt, OrganizationDto organization, QuadrantInfoDto quadrant) {
        this.id = id;
        this.vehiclePlate = vehiclePlate;
        this.type = type;
        this.createdAt = createdAt;
        this.organization = organization;
        this.quadrant = quadrant;
    }

    public VehicleDto(Long id, String vehiclePlate, String type, LocalDateTime createdAt, OrganizationDto organization) {
        this.id = id;
        this.vehiclePlate = vehiclePlate;
        this.type = type;
        this.createdAt = createdAt;
        this.organization = organization;
    }

    public Long getId() {
        return id;
    }

    public String getVehiclePlate() {
        return vehiclePlate;
    }

    public String getType() {
        return type;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public OrganizationDto getOrganization() {
        return organization;
    }

    public QuadrantInfoDto getQuadrant() {
        return quadrant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VehicleDto entity = (VehicleDto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.vehiclePlate, entity.vehiclePlate) &&
                Objects.equals(this.type, entity.type) &&
                Objects.equals(this.createdAt, entity.createdAt) &&
                Objects.equals(this.organization, entity.organization) &&
                Objects.equals(this.quadrant, entity.quadrant);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, vehiclePlate, type, createdAt, organization, quadrant);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "vehiclePlate = " + vehiclePlate + ", " +
                "type = " + type + ", " +
                "createdAt = " + createdAt + ", " +
                "organization = " + organization + ", " +
                "cuadrant = " + quadrant + ")";
    }
}
