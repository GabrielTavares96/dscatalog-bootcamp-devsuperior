package com.devsuperior.dscatalog.tests.integration;

import com.devsuperior.dscatalog.dto.ProductDTO;
import com.devsuperior.dscatalog.services.ProductService;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class ProductServiceIT {

    @Autowired
    private ProductService service;

    private long existingId;
    private long nonExistingId;
    private long countTotalProducts;
    private long countPcGamerProducts;
    private PageRequest pageRequest;

    @BeforeEach
    void setUp() throws Exception {
        existingId = 1l;
        nonExistingId = 1000l;
        countTotalProducts = 25l;
        countPcGamerProducts = 21l;
        pageRequest = PageRequest.of(0, 10);
    }

    @Test
    public void findAllPagedShouldReturnNothingWhenNameDoesNotExist() {
        String name = "Camera";

        Page<ProductDTO> result = service.findAllPaged(0l, name, pageRequest);

        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void findAllPagedShouldReturnAllProductsWhenNameIsEmpty() {
        String name = "";

        Page<ProductDTO> result = service.findAllPaged(0l, name, pageRequest);

        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(countTotalProducts, result.getTotalElements());
    }

    @Test
    public void findAllPagedShouldReturnProductsWhenNameExistsIgnoringCase() {
        String name = "pc gAMeR";

        Page<ProductDTO> result = service.findAllPaged(0l, name, pageRequest);

        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(countPcGamerProducts, result.getTotalElements());
    }

    @Test
    public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.delete(nonExistingId);
        });
    }

    @Test
    public void deleteShouldDoNothingWhenIdExists() {

        Assertions.assertDoesNotThrow(() -> {
            service.delete(existingId);
        });
    }

}
