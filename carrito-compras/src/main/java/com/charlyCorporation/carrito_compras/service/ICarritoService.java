package com.charlyCorporation.carrito_compras.service;

import com.charlyCorporation.carrito_compras.dto.ProductosDTO;
import com.charlyCorporation.carrito_compras.model.Carrito;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz que contiene los metodos genericos
 */
public interface ICarritoService {

     Carrito save(Carrito car);

    Carrito saveCarrito(Long idCarrito,
                            String nomProductos);

     List<Carrito> getCarrito();

    Optional<Carrito> findById(Long idCarrito);

    Optional<Carrito> addProdToCarrito(long idCarrito, String nomProductos);

    List<ProductosDTO> findNombre(String nombre);

    Carrito deleteProd(long idCarrito, String nomProductos);

    void deleteAllCar(Long idCarrito);


}
