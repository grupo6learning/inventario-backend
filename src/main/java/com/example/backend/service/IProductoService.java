package com.example.backend.service;

import java.util.List;
import java.util.Optional;

import com.example.backend.entidad.Producto;

public interface IProductoService {
    public List<Producto> cargarProductos();
    public Producto guardarProducto(Producto producto);
    public Optional<Producto> buscarProducto(int id);
    public void eliminarProducto(int id);

    // Nuevos métodos para el dashboard
    long obtenerTotalProductos();
    long obtenerProductosAgotados();
    double obtenerValorTotalInventario();
    List<Producto> obtenerUltimosProductos();
}
