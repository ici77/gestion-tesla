package com.tesla.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    @JsonIgnoreProperties("ventas") // Evita la recursión infinita
    @Schema(description = "Cliente que realizó la compra")
    private Cliente cliente;
    //cada venta requiere un cliente; cada cliente puede tener muchas ventas. 

    @OneToOne
    @JoinColumn(name = "id_coche", nullable = false) // Cambio aquí para reflejar el nombre correcto
    @JsonIgnoreProperties("venta") // Evita la recursión infinita
    @Schema(description = "Coche vendido en la transacción")
    private Coche coche;
    //un coche solo permite una venta y una venta sólo un coche

    @Schema(description = "Fecha de la venta", example = "2024-02-13")
    private LocalDate fechaVenta;
}
