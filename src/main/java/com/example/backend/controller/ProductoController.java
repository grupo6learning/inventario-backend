package com.example.backend.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.entidad.Producto;
import com.example.backend.service.IProductoService;

@RestController
@RequestMapping("/api")
public class ProductoController {

    @Autowired
    private IProductoService productoService;

    @GetMapping("/productos")
    public List<Producto> listar(){
        return productoService.cargarProductos();
    }

    @PostMapping("/producto")
    public ResponseEntity<Producto> guardar(@RequestBody Producto producto){
        return ResponseEntity.status(HttpStatus.CREATED).body(productoService.guardarProducto(producto));
    }

    @PutMapping("/producto/{id}")
    public ResponseEntity<Producto> actualizar(@PathVariable int id, @RequestBody Producto producto){
        Optional<Producto> productoBuscado = productoService.buscarProducto(id);
        if(productoBuscado.isPresent()){
            Producto productoBD = productoBuscado.get();
            productoBD.setNombre(producto.getNombre());
            productoBD.setCantidad(producto.getCantidad());
            productoBD.setPrecio(producto.getPrecio());
            productoBD.setDescripcion(producto.getDescripcion());
            return ResponseEntity.ok(productoService.guardarProducto(productoBD));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/producto/{id}")
    public ResponseEntity<?> eliminar(@PathVariable int id){
        Optional<Producto> productoBuscado = productoService.buscarProducto(id);
        if(productoBuscado.isPresent()){
            productoService.eliminarProducto(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }


    // Nuevos endpoints para el dashboard
    @GetMapping("/productos/total")
    public ResponseEntity<Long> obtenerTotalProductos() {
        return ResponseEntity.ok(productoService.obtenerTotalProductos());
    }

    @GetMapping("/productos/agotados")
    public ResponseEntity<Long> obtenerProductosAgotados() {
        return ResponseEntity.ok(productoService.obtenerProductosAgotados());
    }

    @GetMapping("/productos/valor-total")
    public ResponseEntity<Double> obtenerValorTotal() {
        return ResponseEntity.ok(productoService.obtenerValorTotalInventario());
    }

    @GetMapping("/productos/ultimas-transacciones")
    public ResponseEntity<List<Producto>> obtenerUltimasTransacciones() {
        return ResponseEntity.ok(productoService.obtenerUltimosProductos());
    }

}
