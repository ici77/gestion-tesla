package com.tesla.controller;

import com.tesla.model.Coche;
import com.tesla.service.CocheService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/coches")
@Tag(name = "Coches", description = "Gestión de coches en el sistema")
public class CocheController {

    @Autowired
    private CocheService cocheService;

    @Operation(summary = "Obtener lista de coches", description = "Devuelve una lista de todos los coches registrados en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Coche.class)))
    })
    @GetMapping
    public ResponseEntity<List<Coche>> listarCoches() {
        List<Coche> coches = cocheService.listarCoches();
        return ResponseEntity.ok(coches);
    }

    @Operation(summary = "Obtener coche por ID", description = "Devuelve los detalles de un coche específico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Coche encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Coche.class))),
            @ApiResponse(responseCode = "404", description = "Coche no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Coche> obtenerCoche(@PathVariable Long id) {
        Optional<Coche> coche = cocheService.obtenerCochePorId(id);
        return coche.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Registrar un nuevo coche", description = "Guarda un nuevo coche en la base de datos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Coche creado correctamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Coche.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta")
    })
    @PostMapping
    public ResponseEntity<Coche> guardarCoche(@RequestBody Coche coche) {
        Coche nuevoCoche = cocheService.guardarCoche(coche);
        return ResponseEntity.status(201).body(nuevoCoche);
    }

    @Operation(summary = "Actualizar datos de un coche", description = "Modifica los datos de un coche existente, incluyendo su precio.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Coche actualizado correctamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Coche.class))),
            @ApiResponse(responseCode = "404", description = "Coche no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Coche> actualizarCoche(@PathVariable Long id, @RequestBody Coche cocheActualizado) {
        Optional<Coche> cocheExistente = cocheService.obtenerCochePorId(id);
        if (cocheExistente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // Obtener coche actual y actualizar datos
        Coche coche = cocheExistente.get();
        coche.setMatricula(cocheActualizado.getMatricula());
        coche.setMarca(cocheActualizado.getMarca());
        coche.setModelo(cocheActualizado.getModelo());
        coche.setColor(cocheActualizado.getColor());
        coche.setPrecio(cocheActualizado.getPrecio());

        // Guardar coche actualizado
        return ResponseEntity.ok(cocheService.guardarCoche(coche));
    }

    @Operation(summary = "Eliminar un coche", description = "Elimina un coche por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Coche eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Coche no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCoche(@PathVariable Long id) {
        if (cocheService.obtenerCochePorId(id).isPresent()) {
            cocheService.eliminarCoche(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
