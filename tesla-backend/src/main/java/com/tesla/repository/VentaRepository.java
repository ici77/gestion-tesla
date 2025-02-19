package com.tesla.repository;

import com.tesla.model.Venta;
import com.tesla.model.Coche;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface VentaRepository extends JpaRepository<Venta, Long> {
    Optional<Venta> findByCoche(Coche coche); // Buscar si un coche ya tiene una venta registrada
}
