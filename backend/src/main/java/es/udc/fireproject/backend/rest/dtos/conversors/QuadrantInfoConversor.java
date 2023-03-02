package es.udc.fireproject.backend.rest.dtos.conversors;

import es.udc.fireproject.backend.model.entities.quadrant.Quadrant;
import es.udc.fireproject.backend.rest.dtos.QuadrantInfoDto;

public class QuadrantInfoConversor {
    private QuadrantInfoConversor() {

    }

    public static QuadrantInfoDto toQuadrantDto(Quadrant quadrant) {
        return new QuadrantInfoDto(quadrant.getId(),
                quadrant.getEscala(),
                quadrant.getNombre());
    }
}
