package es.udc.fireproject.backend.rest.dtos;

import java.time.LocalDateTime;
import java.util.Objects;

public class VehicleDto extends BaseDto {

    private static final long serialVersionUID = -6531781678159378396L;

    private final Long id;
    private final String vehiclePlate;
    private final String type;
    private final LocalDateTime createdAt;
    private final OrganizationDto organization;
    private final CuadrantInfoDto cuadrant;

    public VehicleDto(Long id, String vehiclePlate, String type, LocalDateTime createdAt, OrganizationDto organization, CuadrantInfoDto cuadrant) {
        this.id = id;
        this.vehiclePlate = vehiclePlate;
        this.type = type;
        this.createdAt = createdAt;
        this.organization = organization;
        this.cuadrant = cuadrant;
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

    public CuadrantInfoDto getCuadrant() {
        return cuadrant;
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
                Objects.equals(this.cuadrant, entity.cuadrant);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, vehiclePlate, type, createdAt, organization, cuadrant);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "vehiclePlate = " + vehiclePlate + ", " +
                "type = " + type + ", " +
                "createdAt = " + createdAt + ", " +
                "organization = " + organization + ", " +
                "cuadrant = " + cuadrant + ")";
    }
}
