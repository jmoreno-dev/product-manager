package com.jmoreno_dev.productmanager.service;

import com.jmoreno_dev.productmanager.entity.Product;
import com.jmoreno_dev.productmanager.exceptions.InvalidProductException;
import com.jmoreno_dev.productmanager.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product saveOrUpdateProduct(Product product) throws InvalidProductException {
        ProductValidator.validateProduct(product);
        return productRepository.save(product);
    }

    public boolean deleteProduct(Long id) {
        if (!productRepository.existsById(id)) return false;
        productRepository.deleteById(id);
        return true;
    }
}
