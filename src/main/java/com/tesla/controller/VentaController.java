package com.tesla.controller;

import com.tesla.model.Venta;
import com.tesla.service.VentaService;
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
@RequestMapping("/ventas")
@Tag(name = "Ventas", description = "Gestión de ventas de coches en el sistema")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @Operation(summary = "Obtener lista de ventas", description = "Devuelve una lista de todas las ventas registradas en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Venta.class)))
    })
    @GetMapping
    public ResponseEntity<List<Venta>> listarVentas() {
        List<Venta> ventas = ventaService.listarVentas();
        return ResponseEntity.ok(ventas);
    }

    @Operation(summary = "Obtener venta por ID", description = "Devuelve los detalles de una venta específica.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Venta encontrada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Venta.class))),
            @ApiResponse(responseCode = "404", description = "Venta no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Venta> obtenerVenta(@PathVariable Long id) {
        Optional<Venta> venta = ventaService.obtenerVentaPorId(id);
        return venta.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Registrar una nueva venta", description = "Guarda una nueva venta en la base de datos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Venta creada correctamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Venta.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta")
    })
    @PostMapping
    public ResponseEntity<Venta> guardarVenta(@RequestBody Venta venta) {
        Venta nuevaVenta = ventaService.guardarVenta(venta);
        return ResponseEntity.status(201).body(nuevaVenta);
    }

    @Operation(summary = "Eliminar una venta", description = "Elimina una venta por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Venta eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Venta no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarVenta(@PathVariable Long id) {
        if (ventaService.obtenerVentaPorId(id).isPresent()) {
            ventaService.eliminarVenta(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
