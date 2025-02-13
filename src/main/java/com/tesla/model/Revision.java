package com.tesla.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;


@Data
@Entity
@Table(name = "revisiones")
@Schema(description = "Entidad que representa una revisión de un coche")
public class Revision {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único de la revisión", example = "1")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "coche_id", nullable = false)
    @Schema(description = "Coche al que se le ha realizado la revisión")
    private Coche coche;

    @Schema(description = "Fecha de la revisión", example = "2024-02-13")
    private LocalDate fecha;

    @Schema(description = "Cambio de filtro realizado", example = "true")
    private boolean cambioFiltro;

    @Schema(description = "Cambio de aceite realizado", example = "true")
    private boolean cambioAceite;

    @Schema(description = "Cambio de frenos realizado", example = "false")
    private boolean cambioFrenos;
}
