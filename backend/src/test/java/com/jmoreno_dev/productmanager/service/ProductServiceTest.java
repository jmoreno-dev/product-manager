package com.jmoreno_dev.productmanager.service;

import com.jmoreno_dev.productmanager.entity.Product;
import com.jmoreno_dev.productmanager.exceptions.InvalidProductException;
import com.jmoreno_dev.productmanager.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void testSaveProduct_validProduct_savesAndReturnsProduct() throws InvalidProductException {
        Product validProduct = new Product(null, "Laptop", "High-end gaming laptop", 1500.00, 9);
        Product savedProduct = new Product(1L, "Laptop", "High-end gaming laptop", 1500.00, 9);

        when(productRepository.save(validProduct)).thenReturn(savedProduct);

        Product result = productService.saveOrUpdateProduct(validProduct);

        assertNotNull(result.getId());
        assertEquals("Laptop", result.getName());
        verify(productRepository, times(1)).save(validProduct);
    }

    @Test
    void testSaveProduct_nullName_throwsInvalidProductException() {
        Product invalidProduct = new Product(null, null, "No name product", 100.00, 5);

        assertThrows(InvalidProductException.class, () ->
                productService.saveOrUpdateProduct(invalidProduct));

        verify(productRepository, never()).save(any());
    }

    @Test
    void testSaveProduct_negativePrice_throwsInvalidProductException() {
        Product invalidProduct = new Product(null, "Laptop", "Descripcion", -1.00, 5);

        assertThrows(InvalidProductException.class, () ->
                productService.saveOrUpdateProduct(invalidProduct));

        verify(productRepository, never()).save(any());
    }

    @Test
    void testGetAllProducts_returnsProductList() {
        List<Product> products = List.of(
                new Product(1L, "Laptop", "High-end gaming laptop", 1500.00, 9),
                new Product(2L, "Mouse", "Wireless mouse", 25.00, 50)
        );

        when(productRepository.findAll()).thenReturn(products);

        List<Product> result = productService.getAllProducts();

        assertEquals(2, result.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testGetProductById_existingId_returnsProduct() {
        Product product = new Product(1L, "Laptop", "High-end gaming laptop", 1500.00, 9);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Product result = productService.getProductById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void testGetProductById_nonExistingId_returnsNull() {
        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        Product result = productService.getProductById(99L);

        assertNull(result);
        verify(productRepository, times(1)).findById(99L);
    }

    @Test
    void testDeleteProduct_existingId_returnsTrue() {
        when(productRepository.existsById(1L)).thenReturn(true);

        boolean result = productService.deleteProduct(1L);

        assertTrue(result);
        verify(productRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteProduct_nonExistingId_returnsFalse() {
        when(productRepository.existsById(99L)).thenReturn(false);

        boolean result = productService.deleteProduct(99L);

        assertFalse(result);
        verify(productRepository, never()).deleteById(any());
    }
}