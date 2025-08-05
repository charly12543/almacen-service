package com.charlyCorporation.ventas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarritoDTO {

    private Long idCarrito;
    private Double precioTotal;
    private List<String> nomProductos;
}
