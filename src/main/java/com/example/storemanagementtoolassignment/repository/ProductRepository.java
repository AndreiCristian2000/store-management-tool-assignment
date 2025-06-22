package com.example.storemanagementtoolassignment.repository;

import com.example.storemanagementtoolassignment.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
