package com.tesla.service;

import com.tesla.model.Coche;
import com.tesla.repository.CocheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CocheService {

    @Autowired
    private CocheRepository cocheRepository;

    public List<Coche> listarCoches() {
        return cocheRepository.findAll();
    }

    public Optional<Coche> obtenerCochePorId(Long id) {
        return cocheRepository.findById(id);
    }

    public Coche guardarCoche(Coche coche) {
        return cocheRepository.save(coche);
    }

    public void eliminarCoche(Long id) {
        cocheRepository.deleteById(id);
    }
}

