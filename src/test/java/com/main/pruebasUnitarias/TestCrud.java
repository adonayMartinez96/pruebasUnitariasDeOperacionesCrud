package com.main.pruebasUnitarias;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;


import com.main.pruebasUnitarias.Models.Producto;
import com.main.pruebasUnitarias.Repository.ProductoRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestCrud {
    @Autowired
    private ProductoRepository productoRepository;

    @Test
    @Order(1)
    public void testGuardarProducto(){
        Producto producto = new Producto(5,"audifonos jbl");
        Producto productoGuardado = productoRepository.save(producto);
        assertNotNull(productoGuardado);
    }

    @Test
    @Rollback(false)
    @Order(2)
    public void testBuscarProductoPornombreInexistente(){
        String nombre = "celular samsung";
        Producto producto = productoRepository.findByNombre(nombre);
        assertThat(producto.getNombre()).isEqualTo(nombre);
    }

    @Test
    @Rollback(false)
    @Order(3)
    public void testActualizarProducto(){
        String nombre = "audifonos jbl t100";//nuevo valor
        Producto producto = new Producto(nombre,5000);
        producto.setId(5);//id de producto a actualizar

        productoRepository.save(producto);
        Producto productoActualizado = productoRepository.findByNombre(nombre);
        assertThat(productoActualizado.getNombre()).isEqualTo(nombre);
    }

    @Test
    @Rollback(false)
    public void testListarProductos(){
        List<Producto> Productos = productoRepository.findAll();

        for(Producto producto: Productos){
            System.out.println(producto);
        }

        assertThat(Productos).size().isGreaterThan(0);
    }

    @Test
    @Rollback(false)
    public void testEliminarProducto(){
        Integer id = 5;
        boolean esExistenteAntesDeEliminar = productoRepository.findById(id).isPresent();

        productoRepository.deleteById(id);

        boolean noExisteDespuesDeEliminar = productoRepository.findById(id).isPresent();
        assertTrue(esExistenteAntesDeEliminar);
        assertFalse(noExisteDespuesDeEliminar);
    }


}
