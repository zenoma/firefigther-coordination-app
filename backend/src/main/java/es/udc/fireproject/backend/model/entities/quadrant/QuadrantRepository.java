package es.udc.fireproject.backend.model.entities.quadrant;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuadrantRepository extends JpaRepository<Quadrant, Integer> {

    List<Quadrant> findByEscala(String escala);

    List<Quadrant> findByFireId(Long id);

    List<Quadrant> findByFireIdNotNull();
}
