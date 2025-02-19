package com.tesla.service;

import com.tesla.model.Coche;
import com.tesla.model.Venta;
import com.tesla.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    public List<Venta> listarVentas() {
        return ventaRepository.findAll();
    }

    public Optional<Venta> obtenerVentaPorId(Long idVenta) {
        return ventaRepository.findById(idVenta);
    }

    public Venta guardarVenta(Venta venta) {
        // Verificar si el coche ya ha sido vendido
        Optional<Venta> ventaExistente = ventaRepository.findByCoche(venta.getCoche());
        if (ventaExistente.isPresent()) {
            throw new RuntimeException("Este coche ya ha sido vendido y no puede venderse nuevamente.");
        }

        // Guardar la venta si el coche no ha sido vendido antes
        return ventaRepository.save(venta);
    }

    public void eliminarVenta(Long idVenta) {
        ventaRepository.deleteById(idVenta);
    }
}
