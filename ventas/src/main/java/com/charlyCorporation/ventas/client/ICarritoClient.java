package com.charlyCorporation.ventas.client;

import com.charlyCorporation.ventas.dto.CarritoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Interfaz Cliente que hace la conexion mediante Feign hacia el servidor Eureka
 */
@FeignClient(name = "carrito-compras")
public interface ICarritoClient {

    /**
     * End-point que busca un carrito de compras mediante su Id
     * @param idCarrito
     * @return
     */
    @GetMapping("/carrito/find/{idCarrito}")
    CarritoDTO find(@PathVariable Long idCarrito);
}
