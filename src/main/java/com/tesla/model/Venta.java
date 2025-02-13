package com.tesla.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import io.swagger.v3.oas.annotations.media.Schema;





@Data
@Entity
@Table(name = "ventas")
@Schema(description = "Entidad que representa una venta de un coche a un cliente")
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único de la venta", example = "1")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    @Schema(description = "Cliente que realizó la compra")
    private Cliente cliente;

    @OneToOne
    @JoinColumn(name = "coche_id", nullable = false)
    @Schema(description = "Coche vendido en la transacción")
    private Coche coche;

    @Schema(description = "Fecha de la venta", example = "2024-02-13")
    private LocalDate fechaVenta;
}