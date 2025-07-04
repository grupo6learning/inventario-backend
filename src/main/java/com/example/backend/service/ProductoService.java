package com.example.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backend.dao.IProductoDAO;
import com.example.backend.entidad.Producto;

import jakarta.transaction.Transactional;

@Service
public class ProductoService implements IProductoService {

    @Autowired
    private IProductoDAO productoDAO;

    @Override
    public List<Producto> cargarProductos() {
        return (List<Producto>) productoDAO.findAll();
    }

    @Transactional
    @Override
    public Producto guardarProducto(Producto producto) {
        return productoDAO.save(producto);
    }

    @Override
    public Optional<Producto> buscarProducto(int id) {
        return productoDAO.findById(id);
    }

    @Override
    public void eliminarProducto(int id) {
        productoDAO.deleteById(id);
    }

    // Nuevos métodos para el dashboard
    @Override
    public long obtenerTotalProductos() {
        return productoDAO.count();
    }

    @Override
    public long obtenerProductosAgotados() {
        List<Producto> productos = cargarProductos();
        return productos.stream()
                .filter(p -> p.getCantidad() <= 0) // Usando getCantidad() en lugar de getStock()
                .count();
    }

    @Override
    public double obtenerValorTotalInventario() {
        List<Producto> productos = cargarProductos();
        return productos.stream()
                .mapToDouble(p -> p.getPrecio() * p.getCantidad())
                .sum();
    }

    @Override
    public List<Producto> obtenerUltimosProductos() {
        List<Producto> productos = cargarProductos();
        return productos.stream()
                .limit(5)
                .toList();
    }
}
