package com.charlyCorporation.productos.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * Clase POJO la cual contiene los datos de los productos
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProducto;

    @NotBlank(message = "El campo nombre no puede estar vacio")
    private String nombre;

    @NotBlank(message = "El campo marca no puede estar vacio")
    private String marca;

    @NotNull(message = "El precio no puede estar vacio")
    private Double precio;


}
