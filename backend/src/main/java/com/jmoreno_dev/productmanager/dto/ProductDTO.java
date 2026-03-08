package com.jmoreno_dev.productmanager.dto;

public record ProductDTO(
        Long id,
        String name,
        String description,
        Double price,
        Integer quantity
) { }
