package com.charlyCorporation.ventas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VentasDTO {

    private Long idVenta;
    private LocalDate fechaVenta;
    private Long idCarrito;
    private Double ventaTotal;
    private List<ProductosDTO> listProductos;


}
