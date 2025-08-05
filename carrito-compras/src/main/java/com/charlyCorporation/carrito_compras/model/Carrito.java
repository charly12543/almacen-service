package com.charlyCorporation.carrito_compras.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Clase POJO contiene las propiedades del carrito de compras
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Carrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCarrito;

    @NotNull(message = "El campo precioTotal no puede estar vacio")
    private Double precioTotal;

    @NotNull(message = "El campo nomProductos no puede estar vacio")
    private List<String> nomProductos;

}
