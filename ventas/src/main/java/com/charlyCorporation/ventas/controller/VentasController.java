package com.charlyCorporation.ventas.controller;

import com.charlyCorporation.ventas.dto.VentasDTO;
import com.charlyCorporation.ventas.model.Ventas;
import com.charlyCorporation.ventas.service.IVentasService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Clase controladora que contien los End-points
 */
@RestController
public class VentasController {

    /**
     * Inyeccion de dependencias
     */
    @Autowired
    private IVentasService service;

    /**
     * End-point para guardar una venta
     * @param ventas
     * @return
     */
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('CREATE')")
    public ResponseEntity<?> saveVenta(@Valid @RequestBody Ventas ventas,
                                            BindingResult result){
        Map<String, String> errores = new HashMap<>();
        if(result.hasErrors()){
            result.getFieldErrors().forEach(er -> {
                errores.put(er.getField(), "Error en el campo " +  er.getField()
                +" : " + er.getField());
            });
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.saveVenta(ventas));
    }

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('READ')")
    public ResponseEntity<List<Ventas>> listSold(){
        return ResponseEntity.ok(service.listVentas());
    }

    @GetMapping("/find/{idVenta}")
    @PreAuthorize("hasRole('ADMIN', 'USER')")
    public ResponseEntity<?> findByIdDTO(@PathVariable Long idVenta){
        Optional<VentasDTO> ventas = service.findById(idVenta);
        if(ventas.isPresent()) {
            return ResponseEntity.ok(ventas);
        }
        return ResponseEntity.notFound().build();
    }

}
