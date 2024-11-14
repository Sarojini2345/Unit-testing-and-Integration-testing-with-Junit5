package com.javatechie.crud.example.unittest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.javatechie.crud.example.entity.Product;
import com.javatechie.crud.example.repository.ProductRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // Use H2 or other test DB
@Transactional // Ensure database state is reset after each test
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        // Save some test data in the H2 database
        productRepository.save(new Product("Laptop", 1, 1200));
        productRepository.save(new Product("Phone", 2, 800));
        productRepository.save(new Product("Tablet", 3, 500));
    }

    @Test
    void testFindByPriceGreaterThan() {
        List<Product> expensiveProducts = productRepository.findByPriceGreaterThan(700);

        assertEquals(2, expensiveProducts.size());
        assertTrue(expensiveProducts.stream().allMatch(p -> p.getPrice() > 700));
    }

    @Test
    void testSaveProduct() {
        Product product = new Product("Smart Watch", 1, 300);
        Product savedProduct = productRepository.save(product);

        assertNotNull(savedProduct.getId());
        assertEquals("Smart Watch", savedProduct.getName());
    }
}
