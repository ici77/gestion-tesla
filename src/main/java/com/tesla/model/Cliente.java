package com.tesla.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Entity
@Table(name = "clientes")
@Schema(description = "Entidad que representa a un cliente en el sistema")
public class Cliente {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único del cliente", example = "1")
    private Long id;

    @Schema(description = "NIF del cliente", example = "12345678A")
    private String nif;

    @Schema(description = "Nombre del cliente", example = "Juan Pérez")
    private String nombre;

    @Schema(description = "Dirección del cliente", example = "Calle Falsa 123")
    private String direccion;

    @Schema(description = "Ciudad del cliente", example = "Madrid")
    private String ciudad;

    @Schema(description = "Teléfono del cliente", example = "600123456")
    private String telefono;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    @Schema(description = "Lista de ventas asociadas al cliente")
    private List<Venta> ventas;
}