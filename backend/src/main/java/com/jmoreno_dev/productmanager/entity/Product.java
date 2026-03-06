package com.jmoreno_dev.productmanager.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table (name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "name", nullable = false)
    private String name;

    @Column (name = "description")
    private String description;

    @Column (name = "price", nullable = false)
    private Double price;

    @Column (name = "quantity", nullable = false)
    private Integer quantity;

    public Product() {
    }

    public Product(Long id, String name, String description, Double price, Integer quantity) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }
}
