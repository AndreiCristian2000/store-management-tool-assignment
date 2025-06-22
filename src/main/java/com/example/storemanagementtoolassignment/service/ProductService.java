package com.example.storemanagementtoolassignment.service;

import com.example.storemanagementtoolassignment.model.Product;
import com.example.storemanagementtoolassignment.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository repository;

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    public List<Product> getAllProducts() {
        logger.info("Fetching all products");
        return repository.findAll();
    }

    public Product getProduct(Long id) {
        logger.debug("Fetching product with ID: {}", id);
        return repository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Product with ID {} not found", id);
                    return new RuntimeException("Product not found");
                });
    }

    public Product addProduct(Product product) {
        logger.info("Adding product: {}", product.getName());
        return repository.save(product);
    }

    public Product updatePrice(Long id, Double newPrice) {
        logger.info("Updating price for product with ID {} to {}", id, newPrice);
        Product product = getProduct(id);
        product.setPrice(newPrice);
        return repository.save(product);
    }
}
