package com.charlyCorporation.ventas.service;

import com.charlyCorporation.ventas.dto.VentasDTO;
import com.charlyCorporation.ventas.model.Ventas;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz que contiene los metodos genericos
 */
public interface IVentasService {

    Ventas saveVenta(Ventas ventas);

     List<Ventas> listVentas();

    Optional<Ventas> find(Long idVenta);

    Optional<VentasDTO> findById(Long idVenta);

    void delete(Long id);


}
