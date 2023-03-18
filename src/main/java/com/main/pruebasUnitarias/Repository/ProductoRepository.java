package com.main.pruebasUnitarias.Repository;

import com.main.pruebasUnitarias.Models.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    public Producto findByNombre(String nombre);
}
