package com.tesla.controller;

import com.tesla.model.Revision;
import com.tesla.service.RevisionService;
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
@RequestMapping("/revisiones")
@Tag(name = "Revisiones", description = "Gestión de revisiones de coches en el sistema")
public class RevisionController {

    @Autowired
    private RevisionService revisionService;

    @Operation(summary = "Obtener lista de revisiones", description = "Devuelve una lista de todas las revisiones registradas en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Revision.class)))
    })
    @GetMapping
    public ResponseEntity<List<Revision>> listarRevisiones() {
        List<Revision> revisiones = revisionService.listarRevisiones();
        return ResponseEntity.ok(revisiones);
    }

    @Operation(summary = "Obtener revisión por ID", description = "Devuelve los detalles de una revisión específica.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Revisión encontrada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Revision.class))),
            @ApiResponse(responseCode = "404", description = "Revisión no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Revision> obtenerRevision(@PathVariable Long id) {
        Optional<Revision> revision = revisionService.obtenerRevisionPorId(id);
        return revision.map(ResponseEntity::ok)
                       .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Registrar una nueva revisión", description = "Guarda una nueva revisión en la base de datos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Revisión creada correctamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Revision.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta")
    })
    @PostMapping
    public ResponseEntity<Revision> guardarRevision(@RequestBody Revision revision) {
        Revision nuevaRevision = revisionService.guardarRevision(revision);
        return ResponseEntity.status(201).body(nuevaRevision);
    }

    @Operation(summary = "Eliminar una revisión", description = "Elimina una revisión por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Revisión eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Revisión no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRevision(@PathVariable Long id) {
        if (revisionService.obtenerRevisionPorId(id).isPresent()) {
            revisionService.eliminarRevision(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
