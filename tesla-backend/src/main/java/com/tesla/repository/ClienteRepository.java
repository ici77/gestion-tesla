package com.tesla.repository;

import com.tesla.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
//extiende de JpaRepository<Coche, Long>, hereda muchos métodos útiles
// se encarga de la comunicación directa con la base de datos, proporcionando métodos CRUD
