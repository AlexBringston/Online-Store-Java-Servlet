package com.store.model.dao;

import com.store.model.entity.Category;
import com.store.model.entity.Color;
import com.store.model.entity.Product;
import com.store.model.entity.Size;
import com.store.model.exception.DatabaseException;

import java.util.List;
import java.util.Optional;

public interface ProductDao extends GenericDao<Product>{
    int countAllProducts() throws DatabaseException;

    int countProductsByCategory(String category) throws DatabaseException;

    int countProductsByColor(String color) throws DatabaseException;

    int countProductsBySize(String size) throws DatabaseException;


    Optional<List<Product>> findPerPage(int count, int limit, String sort, String direction, String locale) throws DatabaseException;

    Optional<List<Product>> findPerPageByCategory(int count, String name, String sort, String direction,
                                                  String locale) throws DatabaseException;

    Optional<List<Product>> findPerPageByColor(int count, String name, String sort, String direction, String locale) throws DatabaseException;

    Optional<List<Product>> findPerPageBySize(int count, String name, String sort, String direction, String locale) throws DatabaseException;

    Optional<List<Category>> listAllCategories(String locale) throws DatabaseException;

    Optional<List<Color>> listAllColors(String locale) throws DatabaseException;

    Optional<List<Size>> listAllSizes(String locale) throws DatabaseException;
}
