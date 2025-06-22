package com.example.storemanagementtoolassignment.service;

import com.example.storemanagementtoolassignment.model.Product;
import com.example.storemanagementtoolassignment.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private ProductService productService;

    @Test
    void should_returnAllProducts_when_productsExist() {
        Product p1 = new Product();
        Product p2 = new Product();
        p1.setId(1L);
        p1.setName("Product A");
        p2.setId(2L);
        p2.setName("Product B");

        when(repository.findAll()).thenReturn(Arrays.asList(p1, p2));

        List<Product> result = productService.getAllProducts();

        assertEquals(2, result.size());
        verify(repository).findAll();
    }

    @Test
    void should_returnProduct_when_productExists() {
        Product p = new Product();
        p.setId(1L);
        p.setName("Product Found");

        when(repository.findById(1L)).thenReturn(Optional.of(p));

        Product result = productService.getProduct(1L);

        assertEquals("Product Found", result.getName());
        verify(repository).findById(1L);
    }

    @Test
    void should_throwException_when_productDoesNotExist() {
        when(repository.findById(10L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> productService.getProduct(10L));
        assertEquals("Product not found", ex.getMessage());
    }

    @Test
    void should_saveAndReturnProduct_when_productIsValid() {
        Product p = new Product();
        p.setName("New Product");

        when(repository.save(p)).thenReturn(p);

        Product result = productService.addProduct(p);

        assertEquals("New Product", result.getName());
        verify(repository).save(p);
    }

    @Test
    void should_updatePrice_when_productExists() {
        Product p = new Product();
        p.setId(1L);
        p.setPrice(10.0);

        when(repository.findById(1L)).thenReturn(Optional.of(p));
        when(repository.save(any())).thenAnswer(i -> i.getArgument(0));

        Product result = productService.updatePrice(1L, 99.99);

        assertEquals(99.99, result.getPrice());
        verify(repository).save(p);
    }
}
