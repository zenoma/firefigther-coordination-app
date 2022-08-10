package es.udc.fireproject.backend.model.services.firemanagement;

import es.udc.fireproject.backend.model.entities.cuadrant.Cuadrant;

import java.util.List;

public interface FireManagementService {
    List<Cuadrant> findAllCuadrants();

    List<Cuadrant> findCuadrantsByEscala(String scale);
}
