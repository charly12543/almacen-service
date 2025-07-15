package com.charlyCorporation.productos.controller;

import com.charlyCorporation.productos.model.Producto;
import com.charlyCorporation.productos.service.ProdImp;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
 * Clase controladora que contiene los End-points
 */
@RestController
public class ProductosController {

    /**
     * Inyeccion de Dependencias
     */
    @Autowired
    public ProdImp ser;

    /**
     * Inyectamos el valor del puerto de ejecucion en una variable
     */
    @Value("${server.port}")
    private int serverPort;

    /**
     * Metodo para agregar un producto
     * @param prod
     * @return
     */
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('CREATE')")
    public ResponseEntity<?> saveProducto(@Valid @RequestBody Producto prod,
                                          BindingResult result){
        Map<String, String> errores = new HashMap<>();
        if(result.hasErrors()){
            result.getFieldErrors().forEach(err -> {
                errores.put(err.getField(), "Error! " + err.getField() +
                        " : "  + err.getDefaultMessage());
            });
            return ResponseEntity.badRequest().body(errores);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(ser.saveProducto(prod));
    }

    /**
     * Controlador para obtener el listado de los productos
     * @return
     */
    @GetMapping("/listProductos")
    @PreAuthorize("hasAuthority('READ')")
    public ResponseEntity<List<Producto>> getProductos(){
        if (ser == null) {
            System.out.println(">>> ERROR: ser es null <<<");
        }
        return ResponseEntity.ok(ser.getProductos());
    }

    /**
     * Controlador para encontrar un producto por medio de su Id
     * @param idProducto
     * @return
     */
    @GetMapping("/find/{idProducto}")
    @PreAuthorize("hasRole('ADMIN', 'USER')")
    public ResponseEntity<?> findById(@PathVariable Long idProducto){
        Optional<Producto> prod = ser.findProducto(idProducto);
        return prod.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());


    }

    /**
     * Controlador para encontrar un producto por medio de su nombre
     * @param nombre
     * @return
     */
    @GetMapping("/findByNombre/{nombre}")
    @PreAuthorize("hasRole('ADMIN', 'USER')")
    public  ResponseEntity<?> findByNombre(@PathVariable String nombre){
        Optional<List<Producto>> prod = ser.findProductoByNombre(nombre);
        //Mensaje para probar el puerto donde se esta ejecutando el balanceador de carga
        System.out.println("Probando el balanceador: estoy en el puerto = " + serverPort);
        return prod.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Controlador para eliminar un producto por medio de su Id
     * @param idProducto
     * @return
     */
    @DeleteMapping("/delete/{idProducto}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteProd(@PathVariable Long idProducto){
        Optional<Producto> p = ser.findProducto(idProducto);
        if(p.isPresent()){
            ser.deleteProducto(idProducto);
            return ResponseEntity.ok("Exito en la eliminacion del producto");
        }
        return ResponseEntity.notFound().build();
    }


}
