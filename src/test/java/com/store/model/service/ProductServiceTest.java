package com.store.model.service;

import com.store.model.entity.Product;
import com.store.model.exception.DatabaseException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceTest {

    @Test
    void listAllProducts() throws DatabaseException {
        assertFalse(new ProductService().listAllProducts().isEmpty());
    }

    @Test
    void listProductsPerPage() throws DatabaseException {
        assertFalse(new ProductService().listProductsPerPage(1,8,"name","ASC","").isEmpty());
    }

    @Test
    void listProductsPerPageByColor() throws DatabaseException {
        assertFalse(new ProductService().listProductsPerPageByColor(1,"Red","name","ASC","").isEmpty());
    }

    @Test
    void listProductsPerPageBySize() throws DatabaseException {
        assertFalse(new ProductService().listProductsPerPageBySize(1,"Small","name","ASC","").isEmpty());
    }

    @Test
    void listProductsPerPageByCategory() throws DatabaseException {
        assertFalse(new ProductService().listProductsPerPageByCategory(1,"Man","name","ASC","").isEmpty());
    }

    @Test
    void countAllProducts() throws DatabaseException {
        assertEquals(39,new ProductService().countAllProducts());
    }

    @Test
    void countProductsByCategory() throws DatabaseException {
        assertEquals(13,new ProductService().countProductsByCategory("Woman"));
    }

    @Test
    void countProductsByColor() throws DatabaseException {
        assertEquals(16,new ProductService().countProductsByColor("Red"));
    }

    @Test
    void countProductsBySize() throws DatabaseException {
        assertEquals(13,new ProductService().countProductsBySize("Medium"));
    }

    @Test
    void listAllCategories() throws DatabaseException {
        assertFalse(new ProductService().listAllCategories("").isEmpty());
    }

    @Test
    void listAllColors() throws DatabaseException {
        assertFalse(new ProductService().listAllColors("").isEmpty());
    }

    @Test
    void listAllSizes() throws DatabaseException {
        assertFalse(new ProductService().listAllSizes("").isEmpty());
    }

    @Test
    void getSortDirection() {
        assertEquals("ASC",new ProductService().getSortDirection(""));
    }

    @Test
    void getProductById() throws DatabaseException {
        Product product = new Product();
        product.setId(3);
        assertEquals(product.getId(), new ProductService().getProductById(3,"").getId());
    }
}