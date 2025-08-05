package com.charlyCorporation.productos.repository;

import com.charlyCorporation.productos.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Interfaz que hereda de la clase Abstracta JpaRepository sus metodos abstractos
 */
@Repository
public interface IProductoRepository extends JpaRepository<Producto,Long>{

    /**
     * Consulta personalizada para obtener los productos mediante su nombre
     * @param nombre
     * @return
     */
    @Query("SELECT p FROM Producto p WHERE p.nombre = :nombre")
    List<Producto> findProductoByNombre(String nombre);


}
