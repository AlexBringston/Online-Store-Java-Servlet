package com.store.model.service;


import com.store.model.dao.DaoFactory;
import com.store.model.dao.DeletedProductDao;
import com.store.model.dao.ProductDao;
import com.store.model.entity.Category;
import com.store.model.entity.Color;
import com.store.model.entity.Product;
import com.store.model.entity.Size;
import com.store.model.exception.DatabaseException;
import org.apache.log4j.Logger;


import java.util.List;

public class ProductService {
    private static final Logger log = Logger.getLogger(ProductService.class);

    DaoFactory daoFactory = DaoFactory.getInstance();


    public List<Product> listAllProducts() throws DatabaseException {
        try (ProductDao dao = daoFactory.createProductDao()) {
            return dao.findAll().orElseThrow(() -> new DatabaseException("Could not list products"));
        }
    }

    public void addProduct(Product product) throws DatabaseException {
        try (ProductDao dao = daoFactory.createProductDao()) {
            dao.create(product);
        }
    }

    public void updateProduct(Product product) throws DatabaseException {
        try (ProductDao dao = daoFactory.createProductDao()) {
            dao.update(product);
        }
    }

    public void addDeletedProduct(Product product) throws DatabaseException {
        try (DeletedProductDao dao = daoFactory.createDeletedProductDao()) {
            dao.create(product);
        }
    }

    public void deleteProduct(int productId) throws DatabaseException {
        try (ProductDao dao = daoFactory.createProductDao()) {
            dao.delete(productId);
        }
    }

    public List<Product> listProductsPerPage(int pageNumber, int limit, String sort, String direction, String locale) throws DatabaseException {
        if (sort.equals("size")) {
            sort = "size_id";
        }
        if (sort.equals("novelty")) {
            sort = "created_at";
        }
        try (ProductDao dao = daoFactory.createProductDao()) {
            return dao.findPerPage(pageNumber, limit, sort, direction, locale).orElseThrow(
                    () -> new DatabaseException("Could not list products of page"));
        }
    }

    public List<Product> listProductsPerPageByColor(int number, String name, String sort,
                                                    String direction, String locale) throws DatabaseException {
        if (sort.equals("size")) {
            sort = "size_id";
        }
        if (sort.equals("novelty")) {
            sort = "created_at";
        }
        try (ProductDao dao = daoFactory.createProductDao()) {
            return dao.findPerPageByColor(number, name, sort, direction, locale).orElseThrow(
                    () -> new DatabaseException("Could not list products of page by color"));
        }
    }

    public List<Product> listProductsPerPageBySize(int number, String name, String sort,
                                                   String direction, String locale) throws DatabaseException {
        if (sort.equals("size")) {
            sort = "size_id";
        }
        if (sort.equals("novelty")) {
            sort = "created_at";
        }
        try (ProductDao dao = daoFactory.createProductDao()) {
            return dao.findPerPageBySize(number, name, sort, direction, locale).orElseThrow(
                    () -> new DatabaseException("Could not list products of page by size"));
        }
    }

    public List<Product> listProductsPerPageByCategory(int number, String name, String sort,
                                                       String direction, String locale) throws DatabaseException {
        if (sort.equals("size")) {
            sort = "size_id";
        }
        if (sort.equals("novelty")) {
            sort = "created_at";
        }
        try (ProductDao dao = daoFactory.createProductDao()) {
            return dao.findPerPageByCategory(number, name, sort, direction, locale).orElseThrow(
                    () -> new DatabaseException("Could not list products of page by category"));
        }
    }

    public int countAllProducts() throws DatabaseException {
        try (ProductDao dao = daoFactory.createProductDao()) {
            return dao.countAllProducts();
        }
    }

    public int countProductsByCategory(String category) throws DatabaseException {
        try (ProductDao dao = daoFactory.createProductDao()) {
            return dao.countProductsByCategory(category);
        }
    }

    public int countProductsByColor(String color) throws DatabaseException {
        try (ProductDao dao = daoFactory.createProductDao()) {
            return dao.countProductsByColor(color);
        }
    }

    public int countProductsBySize(String size) throws DatabaseException {
        try (ProductDao dao = daoFactory.createProductDao()) {
            return dao.countProductsBySize(size);
        }
    }

    public List<Category> listAllCategories(String locale) throws DatabaseException {
        try (ProductDao dao = daoFactory.createProductDao()) {
            return dao.listAllCategories(locale).orElseThrow(
                    () -> new DatabaseException("Could not list categories"));
        }
    }

    public List<Color> listAllColors(String locale) throws DatabaseException {
        try (ProductDao dao = daoFactory.createProductDao()) {
            return dao.listAllColors(locale).orElseThrow(
                    () -> new DatabaseException("Could not list colors"));
        }
    }

    public List<Size> listAllSizes(String locale) throws DatabaseException {
        try (ProductDao dao = daoFactory.createProductDao()) {
            return dao.listAllSizes(locale).orElseThrow(
                    () -> new DatabaseException("Could not list sizes"));
        }
    }

    public String getSortDirection(String direction) {
        if (direction.equalsIgnoreCase("ascending") || direction.equalsIgnoreCase("По зростанню") ||
                direction.equals("")) {
            return "ASC";
        } else {
            return "DESC";
        }
    }

    public Product getProductById(int id, String locale) throws DatabaseException {
        try (ProductDao dao = daoFactory.createProductDao()) {
            return dao.findById(id, locale).orElseThrow(() -> new DatabaseException("Could not get product"));
        }
    }
}
