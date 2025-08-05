package com.charlyCorporation.carrito_compras.service;

import com.charlyCorporation.carrito_compras.client.IProductosClient;
import com.charlyCorporation.carrito_compras.dto.ProductosDTO;
import com.charlyCorporation.carrito_compras.model.Carrito;
import com.charlyCorporation.carrito_compras.repository.ICarritoRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Clase que implementa los metodos de ICarritoService
 */
@Service
public class CarritoImp implements ICarritoService {

    /**
     * Inyeccion de dependencias
     */
    @Autowired
    private ICarritoRepository repo;

    /**
     * Inyeccion de dependencia hacia el cliente de Feign
     */
    @Autowired
    private IProductosClient client;

    /**
     * Metodo que permite guardar
     * @param car
     */
    @Override
    @Transactional
    public Carrito save(Carrito car) {

        return repo.save(car);
    }

    /**
     * Metodo que permite guardar los datos de carrito mediante si Id y un producto
     * @param idCarrito
     * @param listProductos
     */
    @Override
    @Transactional
    public Carrito saveCarrito(Long idCarrito,
                               String listProductos) {


        List<String> listProd = new ArrayList<>();
        listProd.add(listProductos);
        List<ProductosDTO> d = client.findByNombre(listProductos);
        Double precio = d.get(0).getPrecio();


        Carrito car = new Carrito();
        car.setIdCarrito(idCarrito);
        car.setPrecioTotal(precio);
        car.setNomProductos(listProd);

        return repo.save(car);
    }

    /**
     * Metodo para obtener el listado de todos los productos
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<Carrito> getCarrito() {
        List<Carrito> lista = repo.findAll();
        return lista;
    }

    /**
     * Metodo para encontrar un carrito de compras mediante su Id
     * @param idCarrito
     * @return
     */
    @Override
    @Transactional
    public Optional<Carrito> findById(Long idCarrito) {
        Optional<Carrito> car = repo.findById(idCarrito);
        return car;
    }


    /**
     * Metodo que permite editar un carrito de comprar agregando un nuevo producto
     * @param idCarrito
     * @param nomProductos
     * @return
     */
    @CircuitBreaker(name = "productos", fallbackMethod = "FallbackAddProdToCarrito")
    @Retry(name = "productos")
    @Override
    @Transactional
    public Optional<Carrito> addProdToCarrito(long idCarrito, String nomProductos) {

        Optional<Carrito> car = this.findById(idCarrito);
        List<ProductosDTO> lisProd = this.findNombre(nomProductos);
        List<String> nuevosProd = car.get().getNomProductos();
        Double precioFinal = car.get().getPrecioTotal() + lisProd.get(0).getPrecio();
        String nuevoNombre = lisProd.get(0).getNombre();
        nuevosProd.add(nuevoNombre);

        Carrito carrito = car.get();

        carrito.setIdCarrito(idCarrito);
        carrito.setPrecioTotal(precioFinal);
        carrito.setNomProductos(nuevosProd);

        this.save(carrito);

        return car;

    }


    /**
     * Metodo para econtrar mediante la conexion de Feign un producto mediante su nombre
     * @param nombre
     * @return
     */
    @CircuitBreaker(name = "productos", fallbackMethod = "FallbackAddProdToCarrito")
    @Retry(name = "productos")
    @Override
    @Transactional
    public List<ProductosDTO> findNombre(String nombre) {

        List<ProductosDTO> prodDTO = client.findByNombre(nombre);
        List<ProductosDTO> listProdDTO = new ArrayList<>();

        for (ProductosDTO dto : prodDTO) {
            if (dto.getNombre().contains(nombre)) {
                ProductosDTO prod = new ProductosDTO();
                prod.setIdProducto(dto.getIdProducto());
                prod.setNombre(dto.getNombre());
                prod.setMarca(dto.getMarca());
                prod.setPrecio(dto.getPrecio());

                listProdDTO.add(prod);
            }

        }

        return listProdDTO;
    }

    /**
     * Metodo para eliminar de un carrito de compras un producto
     * @param idCarrito
     * @param nomProductos
     * @return
     */
    @CircuitBreaker(name = "productos", fallbackMethod = "FallbackAddProdToCarrito")
    @Retry(name = "productos")
    @Override
    @Transactional
    public Carrito deleteProd(long idCarrito, String nomProductos) {

        Optional<Carrito> car = this.findById(idCarrito);
        List<ProductosDTO> prodDTO = this.findNombre(nomProductos);
        Double precioFinal = car.get().getPrecioTotal() - prodDTO.get(0).getPrecio();
        List<String> nuevaList = car.get().getNomProductos();
        nuevaList.remove(nomProductos);

        Carrito carrito = car.get();
        carrito.setIdCarrito(idCarrito);
        carrito.setPrecioTotal(precioFinal);
        carrito.setNomProductos(nuevaList);

        this.save(carrito);

        return carrito;
    }

    /**
     * Metodo para eliminar un carrito de compras
     * @param idCarrito
     */
    @Override
    @Transactional
    public void deleteAllCar(Long idCarrito) {
        repo.deleteById(idCarrito);
    }

    /**
     * Metodo Fallback que permite redirigir el metodo por algun fallo al realizar una peticion
     * @param throwable
     * @return
     */
    public ProductosDTO FallbackAddProdToCarrito(Throwable throwable){
        return new ProductosDTO(99999L, "Error", "Error",00.00);
    }
}
