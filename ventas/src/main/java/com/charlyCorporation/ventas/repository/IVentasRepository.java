package com.charlyCorporation.ventas.repository;

import com.charlyCorporation.ventas.model.Ventas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interfaz que hereda de la clase abstracta JpaRepository sus metodos
 */
@Repository
public interface IVentasRepository extends JpaRepository<Ventas, Long> {
}
