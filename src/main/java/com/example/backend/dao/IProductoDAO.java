package com.example.backend.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.example.backend.entidad.Producto;

public interface IProductoDAO extends CrudRepository<Producto, Integer> {
    @Query("SELECT COUNT(p) FROM Producto p WHERE p.cantidad <= 0")
    long countProductosAgotados();
}
