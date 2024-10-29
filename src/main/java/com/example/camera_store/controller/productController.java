package com.example.camera_store.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.camera_store.service.ProductService;

import jakarta.validation.Valid;
import com.example.camera_store.exception.ResourceNotFoundException;
import com.example.camera_store.model.Product;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;





@RestController
@RequestMapping("api/products")
@Validated
public class productController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getProducts() {
        List<Product> productos = productService.obtenerListadoProducts();
        return ResponseEntity.ok(productos);
    }

    @PostMapping
    public ResponseEntity<Product> ingresarProducto(@Valid @RequestBody Product product) {
        Product nuevoProduct = productService.saveProduct(product);
        return new ResponseEntity<>(nuevoProduct, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
     public ResponseEntity<Product> obtenerProducto(@PathVariable Long id) {
        Product product = productService.obtenerProducto(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product " + id + " not found"));
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        
        productService.obtenerProducto(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product " + id + " not found"));

        productService.deleteProduct(id);

        return 
        ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product payload) {

        Product product = productService.obtenerProducto(id)
            .orElseThrow(() -> new ResourceNotFoundException("Product " + id + " not found"));

        if (payload.getName() != null) {
            product.setName(payload.getName());
        }
        if (payload.getCategory() != null) {
            product.setCategory(payload.getCategory());
        }
        if (payload.getPrice() > 0) {
            product.setPrice(payload.getPrice());
        }
        if (payload.getDescription() != null) {
            product.setDescription(payload.getDescription());
        }

        Product productUpdate = productService.saveProduct(product);
        return ResponseEntity.ok(productUpdate);
    }
    
    
}
