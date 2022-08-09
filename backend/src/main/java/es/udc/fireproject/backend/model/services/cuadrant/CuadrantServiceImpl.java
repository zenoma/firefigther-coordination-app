package es.udc.fireproject.backend.model.services.cuadrant;

import es.udc.fireproject.backend.model.entities.cuadrant.Cuadrant;
import es.udc.fireproject.backend.model.entities.cuadrant.CuadrantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CuadrantServiceImpl implements CuadrantService {

    @Autowired
    CuadrantRepository cuadrantRepository;

    @Override
    public List<Cuadrant> findAll() {
        return cuadrantRepository.findAll();
    }

    @Override
    public List<Cuadrant> findByEscala(String scale) {
        return cuadrantRepository.findByEscala(scale);
    }
}
