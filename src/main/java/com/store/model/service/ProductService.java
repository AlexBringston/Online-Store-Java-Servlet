package com.store.model.service;


import com.store.model.dao.DaoFactory;
import com.store.model.dao.ProductDao;
import com.store.model.entity.Category;
import com.store.model.entity.Color;
import com.store.model.entity.Product;
import com.store.model.entity.Size;
import org.apache.log4j.Logger;


import java.util.List;

public class ProductService {
    private static final Logger log = Logger.getLogger(ProductService.class);

    DaoFactory daoFactory = DaoFactory.getInstance();

    public List<Product> listAllProducts() {
        try (ProductDao dao = daoFactory.createProductDao()) {
            return dao.findAll();
        }
    }

    public List<Product> listProductsPerPage(int number, String sort, String direction) {
        if (sort.equals("size")) {
            sort = "size_id";
        }
        if (sort.equals("novelty")) {
            sort = "created_at";
        }
        try (ProductDao dao = daoFactory.createProductDao()) {
            return dao.findPerPage(number, sort, direction);
        }
    }

    public List<Product> listProductsPerPageByColor(int number, String name, String sort, String direction) {
        if (sort.equals("size")) {
            sort = "size_id";
        }
        if (sort.equals("novelty")) {
            sort = "created_at";
        }
        try (ProductDao dao = daoFactory.createProductDao()) {
            return dao.findPerPageByColor(number, name, sort, direction);
        }
    }

    public List<Product> listProductsPerPageBySize(int number, String name, String sort, String direction) {
        if (sort.equals("size")) {
            sort = "size_id";
        }
        if (sort.equals("novelty")) {
            sort = "created_at";
        }
        try (ProductDao dao = daoFactory.createProductDao()) {
            return dao.findPerPageBySize(number, name, sort, direction);
        }
    }

    public List<Product> listProductsPerPageByCategory(int number, String name, String sort, String direction) {
        if (sort.equals("size")) {
            sort = "size_id";
        }
        if (sort.equals("novelty")) {
            sort = "created_at";
        }
        try (ProductDao dao = daoFactory.createProductDao()) {
            return dao.findPerPageByCategory(number, name, sort, direction);
        }
    }
    public int countAllProducts() {
        try (ProductDao dao = daoFactory.createProductDao()) {
            return dao.countAllProducts();
        }
    }

    public int countProductsByCategory(String category) {
        try (ProductDao dao = daoFactory.createProductDao()) {
            return dao.countProductsByCategory(category);
        }
    }

    public int countProductsByColor(String color) {
        try (ProductDao dao = daoFactory.createProductDao()) {
            return dao.countProductsByColor(color);
        }
    }

    public int countProductsBySize(String size) {
        try (ProductDao dao = daoFactory.createProductDao()) {
            return dao.countProductsBySize(size);
        }
    }

    public List<Category> listAllCategories() {
        try (ProductDao dao = daoFactory.createProductDao()) {
            return dao.listAllCategories();
        }
    }

    public List<Color> listAllColors() {
        try (ProductDao dao = daoFactory.createProductDao()) {
            return dao.listAllColors();
        }
    }
    public List<Size> listAllSizes() {
        try (ProductDao dao = daoFactory.createProductDao()) {
            return dao.listAllSizes();
        }
    }

    public String getSortDirection(String direction) {
        if (direction.equalsIgnoreCase("ascending") || direction.equalsIgnoreCase("По зростанню")) {
            return "ASC";
        } else {
            return "DESC";
        }
    }
}
