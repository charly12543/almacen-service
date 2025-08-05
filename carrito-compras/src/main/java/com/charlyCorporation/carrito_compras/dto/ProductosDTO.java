package com.charlyCorporation.carrito_compras.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase DTO que contiene los atributos de los Productos
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductosDTO {

    private Long idProducto;
    private String nombre;
    private String marca;
    private double precio;
}
