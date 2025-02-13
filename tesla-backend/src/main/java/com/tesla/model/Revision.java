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
    @Column(name = "id_revision") // Cambio aquí para reflejar la nueva nomenclatura
    @Schema(description = "Identificador único de la revisión", example = "1")
    private Long idRevision;

    @ManyToOne
    @JoinColumn(name = "id_coche", nullable = false) // Cambio aquí para reflejar el nombre correcto
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

    @Schema(description = "Observaciones de la revisión", example = "Cambio de filtro y aceite.")
private String observaciones;

}
