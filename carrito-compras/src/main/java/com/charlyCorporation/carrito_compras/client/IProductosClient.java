package com.charlyCorporation.carrito_compras.client;

import com.charlyCorporation.carrito_compras.dto.ProductosDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Interfaz Cliente que hace la conexion mediante Feign hacia el servidor Eureka
 */
@FeignClient(name = "productos")
public interface IProductosClient {

    /**
     * End-point para hacer la conexion entre microservicios
     * permite consultar los productos por medio de su nombre
     * @param nombre
     * @return
     */
    @GetMapping("/producto/findByNombre/{nombre}")
    List<ProductosDTO> findByNombre(@PathVariable String nombre);

}
