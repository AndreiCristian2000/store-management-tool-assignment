package com.example.storemanagementtoolassignment.controller;

import com.example.storemanagementtoolassignment.model.Product;
import com.example.storemanagementtoolassignment.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService service;

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(service.getAllProducts());
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) {
        return ResponseEntity.ok(service.getProduct(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        return ResponseEntity.ok(service.addProduct(product));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/price")
    public ResponseEntity<Product> updatePrice(@PathVariable Long id, @RequestBody Double price) {
        return ResponseEntity.ok(service.updatePrice(id, price));
    }
}
