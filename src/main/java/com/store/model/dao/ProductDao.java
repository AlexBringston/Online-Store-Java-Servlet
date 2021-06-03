package com.store.model.dao;

import com.store.model.entity.Category;
import com.store.model.entity.Color;
import com.store.model.entity.Product;
import com.store.model.entity.Size;

import java.util.List;

public interface ProductDao extends GenericDao<Product>{
    int countAllProducts();

    int countProductsByCategory(String category);

    int countProductsByColor(String color);

    int countProductsBySize(String size);


    List<Product> findPerPage(int count, int limit, String sort, String direction);

    List<Product> findPerPageByCategory(int count, String name, String sort, String direction);

    List<Product> findPerPageByColor(int count, String name, String sort, String direction);

    List<Product> findPerPageBySize(int count, String name, String sort, String direction);

    List<Category> listAllCategories();

    List<Color> listAllColors();

    List<Size> listAllSizes();
}
