package es.udc.fireproject.backend.rest.dtos.conversors;

import es.udc.fireproject.backend.model.entities.cuadrant.Cuadrant;
import es.udc.fireproject.backend.rest.dtos.CuadrantInfoDto;

public class CuadrantInfoConversor {
    private CuadrantInfoConversor() {

    }

    public static CuadrantInfoDto toCuadrantDto(Cuadrant cuadrant) {
        return new CuadrantInfoDto(cuadrant.getId(),
                cuadrant.getEscala(),
                cuadrant.getNombre());
    }
}
