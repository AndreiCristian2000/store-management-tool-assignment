package com.example.storemanagementtoolassignment.controller;

import com.example.storemanagementtoolassignment.config.SecurityConfig;
import com.example.storemanagementtoolassignment.model.Product;
import com.example.storemanagementtoolassignment.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.hamcrest.Matchers.is;

@WebMvcTest(ProductController.class)
@Import(SecurityConfig.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService service;

    @Test
    @WithMockUser(roles = "USER")
    void should_returnProducts_when_userRoleUser() throws Exception {
        Product p1 = new Product();
        Product p2 = new Product();
        p1.setId(1L);
        p1.setName("Product A");
        p2.setId(2L);
        p2.setName("Product B");

        when(service.getAllProducts()).thenReturn(List.of(p1, p2));

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(2)))
                .andExpect(jsonPath("$[0].name", is("Product A")));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void should_addProduct_when_adminRole() throws Exception {
        Product p = new Product();
        p.setId(1L);
        p.setName("New Product");
        p.setPrice(10.0);

        when(service.addProduct(any(Product.class))).thenReturn(p);

        String requestBody = """
           {
               "name": "New Product",
               "price": 10.0
           }
           """;

        mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("New Product")));
    }

    @Test
    @WithMockUser(roles = "USER")
    void should_forbidAddProduct_when_userRole() throws Exception {
        String requestBody = """
           {
               "name": "New Product",
               "price": 10.0
           }
           """;
        mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Access Denied"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void should_updatePrice_when_adminRole() throws Exception {
        Product updated = new Product();
        updated.setId(1L);
        updated.setPrice(99.99);

        when(service.updatePrice(1L, 99.99)).thenReturn(updated);

        mockMvc.perform(put("/products/1/price")
                .contentType(MediaType.APPLICATION_JSON)
                .content("99.99"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price", is(99.99)));
    }

    @Test
    @WithMockUser(roles = "USER")
    void should_forbidUpdatePrice_when_userRole() throws Exception {
        mockMvc.perform(put("/products/1/price")
                .contentType(MediaType.APPLICATION_JSON)
                .content("50.0"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Access Denied"));
    }
}
