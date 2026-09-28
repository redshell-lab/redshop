package com.redshell.redshop.product;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void findAll_shouldReturnAllProducts() {
        List<Product> products = List.of();

        when(productRepository.findAll()).thenReturn(products);

        List<Product> result = productService.findAll();

        assertEquals(products, result);
        verify(productRepository).findAll();
    }

    @Test
    void search_withBlankKeyword_shouldReturnAllProducts() {
        List<Product> products = List.of();

        when(productRepository.findAll()).thenReturn(products);

        List<Product> result = productService.search(" ");

        assertEquals(products, result);
        verify(productRepository).findAll();
        verify(productRepository, never())
                .findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
                        anyString(),
                        anyString()
                );
    }

    @Test
    void findById_whenProductDoesNotExist_shouldThrowException() {
        when(productRepository.findById(999L))
                .thenReturn(java.util.Optional.empty());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> productService.findById(999L)
        );

        assertEquals("Product not found", exception.getMessage());
        verify(productRepository).findById(999L);
    }
}