package com.charlyCorporation.carrito_compras.dto;

import lombok.*;

import java.util.List;

/**
 * Clase DTO que contiene los atributos del carrito de compras
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarritoDTO {

    private Long idCarrito;
    private Double precioTotal;
    private String nomProductos;
    private List<ProductosDTO> listProductos;

    public Long getIdCarrito() {
        return idCarrito;
    }

    public void setIdCarrito(Long idCarrito) {
        this.idCarrito = idCarrito;
    }

    public String getNomProductos() {
        return nomProductos;
    }

    public void setNomProductos(String nomProductos) {
        this.nomProductos = nomProductos;
    }
}
