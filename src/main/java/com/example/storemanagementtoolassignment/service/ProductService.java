package com.example.storemanagementtoolassignment.service;

import com.example.storemanagementtoolassignment.model.Product;
import com.example.storemanagementtoolassignment.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository repository;

    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    public Product getProduct(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public Product addProduct(Product product) {
        return repository.save(product);
    }

    public Product updatePrice(Long id, Double newPrice) {
        Product product = getProduct(id);
        product.setPrice(newPrice);
        return repository.save(product);
    }
}
