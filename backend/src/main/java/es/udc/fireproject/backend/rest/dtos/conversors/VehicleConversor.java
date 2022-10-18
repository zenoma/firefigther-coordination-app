package es.udc.fireproject.backend.rest.dtos.conversors;

import es.udc.fireproject.backend.model.entities.vehicle.Vehicle;
import es.udc.fireproject.backend.rest.dtos.CuadrantInfoDto;
import es.udc.fireproject.backend.rest.dtos.VehicleDto;

public class VehicleConversor {

    private VehicleConversor() {
    }

    public static Vehicle toVehicle(VehicleDto vehicleDto) {
        return new Vehicle(vehicleDto.getVehiclePlate(),
                vehicleDto.getType(),
                OrganizationConversor.toOrganization(vehicleDto.getOrganization()));

    }

    public static VehicleDto toVehicleDto(Vehicle vehicle) {
        CuadrantInfoDto cuadrantInfoDto = new CuadrantInfoDto();
        if (vehicle.getCuadrant() != null) {
            cuadrantInfoDto = CuadrantInfoConversor.toCuadrantDto(vehicle.getCuadrant());
        }

        return new VehicleDto(vehicle.getId(),
                vehicle.getVehiclePlate(),
                vehicle.getType(),
                vehicle.getCreatedAt(),
                OrganizationConversor.toOrganizationDto(vehicle.getOrganization()),
                cuadrantInfoDto);

    }
}
