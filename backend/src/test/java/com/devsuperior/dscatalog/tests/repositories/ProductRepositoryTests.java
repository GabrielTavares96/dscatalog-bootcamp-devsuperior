package com.devsuperior.dscatalog.tests.repositories;

import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.repositories.ProductRepository;
import com.devsuperior.dscatalog.tests.factory.ProductFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@DataJpaTest
public class ProductRepositoryTests {

    @Autowired
    private ProductRepository repository;

    private long existingId;
    private long nonExistingId;
    private long countTotalProducts;
    private long countPcGamerProducts;
    private long countCategory3Products;
    private PageRequest pageRequest;

    @BeforeEach
    void setUp() throws Exception {
        existingId = 1l;
        nonExistingId = 1000l;
        countTotalProducts = 25l;
        countPcGamerProducts = 21l;
        countCategory3Products = 23l;
        pageRequest = PageRequest.of(0, 10);
    }

    @Test
    public void findShouldReturnOnlySelectedCategoryWhenCategoryInformed() {

        List<Category> categories = new ArrayList<>();
        categories.add(new Category(3l, null));

        Page<Product> result = repository.find(categories, "", pageRequest);

        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(countCategory3Products, result.getTotalElements());
    }

    @Test
    public void findShouldReturnAllProductsWhenCategoryNotInformed() {

        List<Category> categories = null;

        Page<Product> result = repository.find(categories, "", pageRequest);

        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(countTotalProducts, result.getTotalElements());
    }

    @Test
    public void findShouldReturnAllProductsWhenNameIsEmpty() {
        String name = "";

        Page<Product> result = repository.find(null, name, pageRequest);

        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(countTotalProducts, result.getTotalElements());
    }

    @Test
    public void findShouldReturnProductsWhenNameExistsIgnoringCase() {
        String name = "pc gAMeR";

        Page<Product> result = repository.find(null, name, pageRequest);

        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(countPcGamerProducts, result.getTotalElements());
    }

    @Test
    public void findShouldReturnProductsWhenNameExists() {
        String name = "PC Gamer";

        Page<Product> result = repository.find(null, name, pageRequest);

        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(countPcGamerProducts, result.getTotalElements());
    }

    @Test
    public void saveShouldPersistWithAutoincrementWhenIdIsNull() {

        Product product = ProductFactory.createProduct();
        product.setId(null);

        product = repository.save(product);
        Optional<Product> result = repository.findById(product.getId());

        Assertions.assertNotNull(product.getId());
        Assertions.assertEquals(countTotalProducts + 1l, product.getId());
        Assertions.assertTrue(result.isPresent());
        Assertions.assertSame(result.get(), product);
    }


    @Test
    public void deleteShouldDeleteObjectWhenIdExists() {

        repository.deleteById(existingId);

        Optional<Product> result = repository.findById(existingId);

        Assertions.assertFalse(result.isPresent());
    }

    @Test
    public void deleteShouldThrowEmptyResultDataAccessExceptionWhenIdDoesNotExists() {

        Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
            repository.deleteById(nonExistingId);
        });
    }

}
