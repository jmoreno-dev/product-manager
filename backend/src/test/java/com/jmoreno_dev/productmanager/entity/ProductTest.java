package com.jmoreno_dev.productmanager.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ProductTest {

    @Test
    void testProductConstructorsAndGettersSetters() {
        Long expectedId = 1L;
        String expectedName = "Test Product";
        String expectedDescription = "Test Description";
        Double expectedPrice = 9.99;
        Integer expectedQuantity = 10;

        Product product = new Product(expectedId, expectedName, expectedDescription, expectedPrice, expectedQuantity);

        assertEquals(expectedId, product.getId());
        assertEquals(expectedName, product.getName());
        assertEquals(expectedDescription, product.getDescription());
        assertEquals(expectedPrice, product.getPrice());
        assertEquals(expectedQuantity, product.getQuantity());

        Product productEmpty = new Product();
        productEmpty.setId(2L);
        productEmpty.setName("Empty Product");
        productEmpty.setDescription("Empty Description");
        productEmpty.setPrice(19.99);
        productEmpty.setQuantity(5);

        assertEquals(2L, productEmpty.getId());
        assertEquals("Empty Product", productEmpty.getName());
        assertEquals("Empty Description", productEmpty.getDescription());
        assertEquals(19.99, productEmpty.getPrice());
        assertEquals(5, productEmpty.getQuantity());
    }
}