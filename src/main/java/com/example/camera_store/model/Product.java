package com.example.camera_store.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;

@Entity
@Table(name = "Product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El nombre no puede ser nulo")
    @Size(min = 5, max = 100, message = "EL titulo del producto debe encontrarse entre 5 y 100 caracteres")
    private String name;

    @Min(value = 1, message = "El precio del producto debe ser almenos de 1 peso")
    private int price;

    @NotNull(message = "La categoria del producto no puede estar en null")
    @Size(min = 4, max = 20, message = "La categoria debe encontrarse entre 5 y 20 caracteres")
    private String category;

    @NotNull(message = "La descripcion del producto no puede estar en null")
    @Size(min = 5, max = 200, message = "La descripcion debe encontrarse entre 5 y 200 caracteres")
    private String description;

    // GET

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    // SET
    // Al ser generado automaticamente no deberia dar acceso a la edicion. // se
    // comenta

    // public void setId(Long id){
    // this.id = id;
    // }

    public void setName( String name ) {
        this.name = name;
    }

    public void setPrice( int price ) {
        this.price = price;
    }

    public void setCategory( String category ){
        this.category = category;
    }

    public void setDescription( String description ){
        this.description = description;
    }
}
