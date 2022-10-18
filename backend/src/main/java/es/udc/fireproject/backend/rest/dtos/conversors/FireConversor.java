package es.udc.fireproject.backend.rest.dtos.conversors;

import es.udc.fireproject.backend.model.entities.cuadrant.Cuadrant;
import es.udc.fireproject.backend.model.entities.fire.Fire;
import es.udc.fireproject.backend.rest.dtos.CuadrantInfoDto;
import es.udc.fireproject.backend.rest.dtos.FireDto;

import java.util.ArrayList;
import java.util.List;

public class FireConversor {

    private FireConversor() {
    }

    public static FireDto toFireDto(Fire fire) {

        List<CuadrantInfoDto> cuadrantDtoList = new ArrayList<>();
        if (fire.getCuadrantGids() != null && !fire.getCuadrantGids().isEmpty()) {
            for (Cuadrant cuadrant : fire.getCuadrantGids()) {
                cuadrantDtoList.add(CuadrantInfoConversor.toCuadrantDto(cuadrant));
            }
        }
        return new FireDto(fire.getDescription(), fire.getType(), fire.getFireIndex(), cuadrantDtoList, fire.getCreatedAt(), fire.getExtinguishedAt());
    }

    public static Fire toFire(FireDto fireDto) {

        return new Fire(fireDto.getDescription(), fireDto.getType(), fireDto.getFireIndex());
    }
}
