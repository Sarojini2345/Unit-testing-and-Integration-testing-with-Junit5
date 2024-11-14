package com.javatechie.crud.example.unittest;

import com.javatechie.crud.example.entity.Product;
import com.javatechie.crud.example.repository.ProductRepository;
import com.javatechie.crud.example.service.ProductService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddProduct() {
        Product product = new Product("Laptop", 1, 1200);
        when(productRepository.save(product)).thenReturn(product);

        Product savedProduct = productService.saveProduct(product);

        assertNotNull(savedProduct);
        assertEquals("Laptop", savedProduct.getName());
        assertEquals(1200, savedProduct.getPrice());
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void testGetAllProducts() {
        List<Product> products = Arrays.asList(
                new Product("Laptop", 1, 1200),
                new Product("Phone", 2, 800)
        );
        when(productRepository.findAll()).thenReturn(products);

        List<Product> result = productService.getProducts();

        assertEquals(1, result.size());
        assertEquals("Laptop", result.get(0).getName());
        verify(productRepository, times(1)).findAll();
    }

   
    @Test
    void testGetProductById() {
        Product product = new Product("Tablet", 3, 500);
        when(productRepository.findById(1)).thenReturn(Optional.of(product));

        Product foundProduct = productService.getProductById(1);

        assertNotNull(foundProduct, "Product should not be null");
        assertEquals("Tablet", foundProduct.getName());
        assertEquals(500, foundProduct.getPrice());
        verify(productRepository, times(1)).findById(1);
    }


    @Test
    void testUpdateProduct() {
        Product existingProduct = new Product("Watch", 1, 250);
        Product updatedProduct = new Product("Smart Watch", 1, 300);

        when(productRepository.findById(1)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(existingProduct)).thenReturn(existingProduct);

        Product result = productService.updateProduct(1, updatedProduct);

        assertEquals("Smart Watch", result.getName());
        assertEquals(300, result.getPrice());
        verify(productRepository, times(1)).findById(1);
        verify(productRepository, times(1)).save(existingProduct);
    }

    @Test
    void testDeleteProduct() {
        doNothing().when(productRepository).deleteById(1);

        productService.deleteProduct(1);

        verify(productRepository, times(1)).deleteById(1);
    }
}
