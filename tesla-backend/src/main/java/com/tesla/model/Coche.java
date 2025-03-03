package com.tesla.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Data
@Entity
@Table(name = "coches")
@Schema(description = "Entidad que representa un coche en el sistema")
public class Coche {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_coche") // Cambio aquí para reflejar la nueva nomenclatura
    @Schema(description = "Identificador único del coche", example = "1")
    private Long idCoche;

    @Schema(description = "Matrícula del coche", example = "1234ABC")
    private String matricula;

    @Schema(description = "Marca del coche", example = "Tesla")
    private String marca;

    @Schema(description = "Modelo del coche", example = "Model S")
    private String modelo;

    @Schema(description = "Color del coche", example = "Rojo")
    private String color;

    @Schema(description = "Precio del coche", example = "75000")
    private Double precio;

    @OneToOne(mappedBy = "coche", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("coche") // Evita la recursión infinita en JSON
    @Schema(description = "Venta asociada al coche")
    private Venta venta;// si borramos coche borramos todo lo asociado a coche

    @OneToMany(mappedBy = "coche", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("coche") // Evita la recursión infinita en JSON
    @Schema(description = "Lista de revisiones asociadas al coche")
    private List<Revision> revisiones;
    //una revisión sólo permite un coche; un coche muchas revisiones
}
