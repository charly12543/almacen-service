package com.charlyCorporation.carrito_compras.repository;

import com.charlyCorporation.carrito_compras.model.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interfaz que hereda de la clas abstracta JpaRepository sus metodos
 */
@Repository
public interface ICarritoRepository extends JpaRepository<Carrito, Long> {
}
