package es.udc.fireproject.backend.model.services.cuadrant;

import es.udc.fireproject.backend.model.entities.cuadrant.Cuadrant;

import java.util.List;

public interface CuadrantService {
    List<Cuadrant> findAll();

    List<Cuadrant> findByEscala(String scale);
}
