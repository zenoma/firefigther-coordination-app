package es.udc.fireproject.backend.rest.dtos.conversors;

import es.udc.fireproject.backend.model.entities.cuadrant.Cuadrant;
import es.udc.fireproject.backend.rest.dtos.CuadrantDto;

import java.util.ArrayList;
import java.util.Arrays;

public class CuadrantConversor {
    private CuadrantConversor() {

    }

    public static CuadrantDto toCuadrantDto(Cuadrant cuadrant) {
        return new CuadrantDto(cuadrant.getId(),
                cuadrant.getEscala(),
                cuadrant.getNombre(),
                cuadrant.getFolla50(),
                cuadrant.getFolla25(),
                cuadrant.getFolla5(),
                new ArrayList(Arrays.asList(cuadrant.getGeom().getCoordinates())));
    }
}
