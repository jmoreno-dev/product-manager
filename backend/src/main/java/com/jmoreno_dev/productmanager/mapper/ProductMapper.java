package com.jmoreno_dev.productmanager.mapper;

import com.jmoreno_dev.productmanager.dto.CreateProductRequest;
import com.jmoreno_dev.productmanager.dto.ProductDTO;
import com.jmoreno_dev.productmanager.entity.Product;

public class ProductMapper {

    public static ProductDTO toDTO(Product product) {
        if (product == null) return null;
        return new ProductDTO(
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getQuantity()
        );
    }

    public static Product toEntity(CreateProductRequest request) {
        if (request == null) {
            return null;
        }

        Product product = new Product();
        product.setName(request.name());
        product.setDescription(request.description());
        product.setPrice(request.price());
        product.setQuantity(request.quantity());

        // El ID no se setea porque lo genera la base de datos
        return product;
    }
}
