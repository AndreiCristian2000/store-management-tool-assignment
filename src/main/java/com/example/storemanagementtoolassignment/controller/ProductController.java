package com.example.storemanagementtoolassignment.controller;

import com.example.storemanagementtoolassignment.model.Product;
import com.example.storemanagementtoolassignment.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(service.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) {
        return ResponseEntity.ok(service.getProduct(id));
    }

    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        return ResponseEntity.ok(service.addProduct(product));
    }

    @PutMapping("/{id}/price")
    public ResponseEntity<Product> updatePrice(@PathVariable Long id, @RequestBody Double price) {
        return ResponseEntity.ok(service.updatePrice(id, price));
    }
}
