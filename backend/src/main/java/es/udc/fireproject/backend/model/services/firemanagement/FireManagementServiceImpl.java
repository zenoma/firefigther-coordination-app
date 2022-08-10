package es.udc.fireproject.backend.model.services.firemanagement;

import es.udc.fireproject.backend.model.entities.cuadrant.Cuadrant;
import es.udc.fireproject.backend.model.entities.cuadrant.CuadrantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FireManagementServiceImpl implements FireManagementService {

    @Autowired
    CuadrantRepository cuadrantRepository;

    @Override
    public List<Cuadrant> findAllCuadrants() {
        return cuadrantRepository.findAll();
    }

    @Override
    public List<Cuadrant> findCuadrantsByEscala(String scale) {
        return cuadrantRepository.findByEscala(scale);
    }
}
