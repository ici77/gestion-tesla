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

    public Optional<Coche> obtenerCochePorId(Long idCoche) { // Cambio aquí
        return cocheRepository.findById(idCoche);
    }
    public Coche actualizarCoche(Coche coche) {
        return cocheRepository.save(coche);
    }
    

    public Coche guardarCoche(Coche coche) {
        return cocheRepository.save(coche);
    }

    public void eliminarCoche(Long idCoche) { // Cambio aquí
        cocheRepository.deleteById(idCoche);
    }
}
//Contiene la lógica de negocio y llama al repositorio.
//se podría eliminar y llamar en el controlador directamente al repositorio
// el servicio maneja las reglas de negocio por ejemplo limita eliminar un coche si tiene venta asociada