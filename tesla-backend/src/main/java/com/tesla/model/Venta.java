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
    @Column(name = "id_venta") // Cambio aquí para reflejar la nueva nomenclatura
    @Schema(description = "Identificador único de la venta", example = "1")
    private Long idVenta;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false) // Cambio aquí para reflejar el nombre correcto
    @Schema(description = "Cliente que realizó la compra")
    private Cliente cliente;

    @OneToOne
    @JoinColumn(name = "id_coche", nullable = false) // Cambio aquí para reflejar el nombre correcto
    @Schema(description = "Coche vendido en la transacción")
    private Coche coche;

    @Schema(description = "Fecha de la venta", example = "2024-02-13")
    private LocalDate fechaVenta;
}
