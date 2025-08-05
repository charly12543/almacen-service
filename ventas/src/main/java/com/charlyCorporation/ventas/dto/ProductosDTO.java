package com.charlyCorporation.ventas.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductosDTO {

    private Long idProducto;
    private String nombre;
    private String marca;
    private double precio;
}
