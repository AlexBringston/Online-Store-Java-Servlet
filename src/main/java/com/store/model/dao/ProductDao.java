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


    List<Product> findPerPage(int count, int limit, String sort, String direction, String locale);

    List<Product> findPerPageByCategory(int count, String name, String sort, String direction, String locale);

    List<Product> findPerPageByColor(int count, String name, String sort, String direction, String locale);

    List<Product> findPerPageBySize(int count, String name, String sort, String direction, String locale);

    List<Category> listAllCategories(String locale);

    List<Color> listAllColors(String locale);

    List<Size> listAllSizes(String locale);
}
