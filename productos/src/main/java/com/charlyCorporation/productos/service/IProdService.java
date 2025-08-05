package com.charlyCorporation.productos.service;

import com.charlyCorporation.productos.model.Producto;

import java.util.List;
import java.util.Optional;

/**
 * Metodos genericos para la implementacion de los productos existentes
 */
public interface IProdService {

    /**
     * Metodo para obetener listado de todos los productos
     * @return
     */
    List<Producto> getProductos();

    /**
     * Metodo para añadir un nuevo producto
     * @param producto
     */
    Producto saveProducto(Producto producto);

    /**
     * Metodo para buscar un producto por su id
     * @param idProducto
     * @return
     */
    Optional<Producto> findProducto(Long idProducto);

    /**
     * Metodo para eliminar un producto por su id
     * @param idProducto
     */
    void deleteProducto(Long idProducto);

    /**
     * Metodo para editar la informacion de un producto
     * @param idProducto
     * @param nombre
     * @param marca
     * @param precio
     * @return
     */
    Producto editProducto(Long idProducto,
                          String nombre,
                          String marca,
                          double precio);

    /**
     * Metodo para buscar informacion de un producto por su nombre
     * @param nombre
     * @return
     */
    Optional<List<Producto>> findProductoByNombre(String nombre);



}
