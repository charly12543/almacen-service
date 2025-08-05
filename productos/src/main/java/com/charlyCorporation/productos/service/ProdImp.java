package com.charlyCorporation.productos.service;

import com.charlyCorporation.productos.model.Producto;
import com.charlyCorporation.productos.repository.IProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Clase que implementa los metodos de la interfaz IProductoService
 */
@Service
public class ProdImp implements IProdService {

    /**
     * Inyeccion de dependecia
     */
    @Autowired
    private IProductoRepository repo;


    /**
     * Metodo que muestra el listado de todos los productos existentes
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<Producto> getProductos() {
        List<Producto> listaProd = repo.findAll();
        return listaProd;
    }

    /**
     * Metodo que guarda un producto
     * @param producto
     */
    @Override
    @Transactional
    public Producto saveProducto(Producto producto) {
        return repo.save(producto);

    }

    /**
     * Metodo para encontrar un producto especifico por su Id
     * @param idProducto
     * @return
     */
    @Override
    @Transactional
    public Optional<Producto> findProducto(Long idProducto) {

        return repo.findById(idProducto);
    }

    /**
     * Metodo para eliminar un producto por su Id
     * @param idProducto
     */
    @Override
    @Transactional
    public void deleteProducto(Long idProducto) {
        repo.deleteById(idProducto);

    }

    /**
     * Metodo que permite editar la informacion de un producto
     * @param idProducto
     * @param nombre
     * @param marca
     * @param precio
     * @return
     */
    @Override
    @Transactional
    public Producto editProducto(Long idProducto,
                                 String nombre,
                                 String marca,
                                 double precio) {

        Optional<Producto> prod = this.findProducto(idProducto);
        prod.get().setNombre(nombre);
        prod.get().setMarca(marca);
        prod.get().setPrecio(precio);

        this.saveProducto(prod.orElse(null));

        return prod.orElse(null);

    }

    /**
     * Metodo para encontrar un producto mediante su nombre
     * @param nombre
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<List<Producto>> findProductoByNombre(String nombre) {
        List<Producto> producto = repo.findProductoByNombre(nombre);
        if(producto.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(producto);
    }

}
