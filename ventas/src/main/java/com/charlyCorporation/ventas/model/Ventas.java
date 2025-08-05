package com.charlyCorporation.ventas.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Clase POJO contiene las propiedades de las Ventas
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ventas {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVenta;

    @NotNull(message = "El campo fechaVenta no puede estar vacio")
    @Temporal(TemporalType.DATE)
    private LocalDate fechaVenta;

    @NotNull(message = "El campo idCarrito no puede estar vacio")
    private Long idCarrito;

}
