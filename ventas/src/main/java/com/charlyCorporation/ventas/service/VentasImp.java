package com.charlyCorporation.ventas.service;

import com.charlyCorporation.ventas.client.ICarritoClient;
import com.charlyCorporation.ventas.client.IproductoClient;
import com.charlyCorporation.ventas.dto.CarritoDTO;
import com.charlyCorporation.ventas.dto.ProductosDTO;
import com.charlyCorporation.ventas.dto.VentasDTO;
import com.charlyCorporation.ventas.model.Ventas;
import com.charlyCorporation.ventas.repository.IVentasRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Clase que implementa los metodos de la interfaz IVentasService
 */
@Service
public class VentasImp implements IVentasService {

    /**
     * Inyeccion de dependecias
     */
    @Autowired
    private IVentasRepository repo;

    /**
     * Inyeccion de dependencia para el consumo del cliente feign de la clase Carrito
     */
    @Autowired
    private ICarritoClient carritoClient;

    @Autowired
    private IproductoClient prodClient;

    @Override
    @Transactional
    public Ventas saveVenta(Ventas ventas) {

        return repo.save(ventas);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Ventas> listVentas() {
        List<Ventas> list = repo.findAll();
        return list;
    }

    @Override
    @Transactional
    public Optional<Ventas> find(Long idVenta) {
        Optional<Ventas> venta = repo.findById(idVenta);
        return venta;
    }

    @CircuitBreaker(name = "productos", fallbackMethod = "fallbackfindById")
    @Retry(name = "productos")
    @Override
    @Transactional
    public Optional<VentasDTO> findById(Long idVenta) {

        Optional<Ventas> venta = this.find(idVenta);
        CarritoDTO carritoDTO = carritoClient.find(venta.get().getIdCarrito());
        List<String> listNomProd = carritoDTO.getNomProductos();
        List<ProductosDTO> newList = new ArrayList<>();

        for (String nom : listNomProd){
            List<ProductosDTO> listProducts = prodClient.findByNombre(nom);
            for(ProductosDTO dto : listProducts) {
                if (dto.getNombre().contains(nom)) {

                    ProductosDTO prod = new ProductosDTO();
                    prod.setIdProducto(dto.getIdProducto());
                    prod.setNombre(dto.getNombre());
                    prod.setMarca(dto.getMarca());
                    prod.setPrecio(dto.getPrecio());

                    newList.add(prod);

                }
            }
        }

        VentasDTO ventasDTO = new VentasDTO();
        ventasDTO.setIdVenta(venta.get().getIdVenta());
        ventasDTO.setFechaVenta(venta.get().getFechaVenta());
        ventasDTO.setIdCarrito(carritoDTO.getIdCarrito());
        ventasDTO.setVentaTotal(carritoDTO.getPrecioTotal());
        ventasDTO.setListProductos(newList);

        //creatException();

        return Optional.of(ventasDTO);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repo.deleteById(id);
    }


    /**
     * Metodo Fallback que permite redirigir el metodo por algun fallo al realizar una peticion
     * @param throwable
     * @return
     */
    public VentasDTO fallbackfindById(Throwable throwable){
        //return new ProductosDTO(99999L, "Error", "Error",00.00);
        return new VentasDTO(9999L,null,99999L,0.0,null);
    }

    public void creatException(){
        throw  new IllegalArgumentException("Prueba de circuitBreaker");
    }
}


