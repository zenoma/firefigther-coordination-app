package es.udc.fireproject.backend.model.entities.cuadrant;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CuadrantRepository extends JpaRepository<Cuadrant, Integer> {

    List<Cuadrant> findByEscala(String escala);

    List<Cuadrant> findByFireId(Long id);
}
