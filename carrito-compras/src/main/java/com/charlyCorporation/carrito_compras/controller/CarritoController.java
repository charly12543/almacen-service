package com.charlyCorporation.carrito_compras.controller;

import com.charlyCorporation.carrito_compras.dto.CarritoDTO;
import com.charlyCorporation.carrito_compras.dto.ProductosDTO;
import com.charlyCorporation.carrito_compras.model.Carrito;
import com.charlyCorporation.carrito_compras.service.ICarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Clase controladora que contien los End-points
 */
@RestController
public class CarritoController {

    /**
     * Inyeccion de dependencias
     */
    @Autowired
    private ICarritoService service;

    /**
     * Inyectamos el valor del puerto de ejecucion en una variable
     */
    @Value("${server.port}")
    private int serverPort;

    /**
     * End-point para guardar un carrito de compras
     * @param
     * @return
     */
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('CREATE')")
    public ResponseEntity<?> saveCarrito(@RequestBody CarritoDTO d){
        return ResponseEntity.status(HttpStatus.CREATED).body(
                (service.saveCarrito(d.getIdCarrito(),d.getNomProductos())));
    }

    /**
     * End-point que muestra la lista de todos los carritos
     * @return
     */
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('READ')")
    public ResponseEntity<List<Carrito>> listaCarrito(){
        return ResponseEntity.ok(service.getCarrito());
    }

    /**
     * End-point para encontraer un producto mediante su nombre
     * @param nombre
     * @return
     */
    @GetMapping("/findByNombre/{nombre}")
    @PreAuthorize("hasRole('ADMIN', 'USER')")
    public ResponseEntity<?> findByNombre(@PathVariable String nombre ){
        List<ProductosDTO> list = service.findNombre(nombre);
        return ResponseEntity.ok(list);
    }

    /**
     * End-point para encontrar un carrito mediante su Id
     * @param idCarrito
     * @return
     */
    @GetMapping("/find/{idCarrito}")
    @PreAuthorize("hasRole('ADMIN', 'USER')")
    public ResponseEntity<?> find(@PathVariable Long idCarrito){
        Optional<Carrito> car = service.findById(idCarrito);
        if(car.isPresent()){
            return ResponseEntity.ok(car);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * End-point para agregar un producto mediante su nombre a un carrito de compras
     * @param idCarrito
     * @param nomProductos
     * @return
     */
    @PutMapping("/add/{idCarrito}")
    @PreAuthorize("hasRole('ADMIN', 'USER')")
    public ResponseEntity<?> addProdToCar(@PathVariable long idCarrito,
                                 @RequestParam(required = false, name = "nombre")
                                 String nomProductos){
        System.out.println("Probando el balanceador " + serverPort);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(service.addProdToCarrito(idCarrito,nomProductos));
    }

    /**
     * End-point para eliminar un producto mediante su nombre a un carrito de compras
     * @param idCarrito
     * @param nomProductos
     * @return
     */
    @PutMapping("/remove/{idCarrito}")
    @PreAuthorize("hasRole('ADMIN', 'USER')")
    public ResponseEntity<?> removeProdToCar(@PathVariable long idCarrito,
                                 @RequestParam(required = false, name = "nombre")
                                 String nomProductos){
        service.deleteProd(idCarrito,nomProductos);
        return ResponseEntity.noContent().build();
    }

    /**
     * End-point para eliminar el carrito de compras mediante su Id
     * @param idCarrito
     * @return
     */
    @GetMapping("/delete/{idCarrito}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteCar(@PathVariable Long idCarrito){
        Optional<Carrito> car = service.findById(idCarrito);
        if(car.isPresent()) {
            service.deleteAllCar(idCarrito);
            return ResponseEntity.noContent().build();

        }
        return ResponseEntity.notFound().build();
    }


}
