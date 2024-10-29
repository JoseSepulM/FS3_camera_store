package com.example.camera_store.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.camera_store.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    
}
