package com.jmoreno_dev.productmanager.dto;

public record CreateProductRequest(
        String name,
        String description,
        Double price,
        Integer quantity
) {
}
