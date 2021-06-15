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

/**
 * Product service which contains methods that create product dao instances and use their methods.
 *
 * @author Alexander Mulyk
 * @since 2021-06-14
 */
public class ProductService {
    private static final Logger log = Logger.getLogger(ProductService.class);

    DaoFactory daoFactory = DaoFactory.getInstance();

    /**
     * Method to list all products in system
     * @return list of products
     * @throws DatabaseException if error in sql has occurred
     */
    public List<Product> listAllProducts() throws DatabaseException {
        try (ProductDao dao = daoFactory.createProductDao()) {
            return dao.findAll().orElseThrow(() -> new DatabaseException("Could not list products"));
        }
    }

    /**
     * Method to add product
     * @param product instance which contains data to be added
     * @throws DatabaseException if error in sql has occurred
     */
    public void addProduct(Product product) throws DatabaseException {
        try (ProductDao dao = daoFactory.createProductDao()) {
            dao.create(product);
        }
    }

    /**
     * Method to update product
     * @param product instance which contains data to be updated
     * @throws DatabaseException if error in sql has occurred
     */
    public void updateProduct(Product product) throws DatabaseException {
        try (ProductDao dao = daoFactory.createProductDao()) {
            dao.update(product);
        }
    }

    /**
     * Method to add product in deleted
     * @param product instance which contains data to be added
     * @throws DatabaseException if error in sql has occurred
     */
    public void addDeletedProduct(Product product) throws DatabaseException {
        try (DeletedProductDao dao = daoFactory.createDeletedProductDao()) {
            dao.create(product);
        }
    }

    /**
     * Method to delete product by given id
     * @param productId id of product to be deleted
     * @throws DatabaseException if error in sql has occurred
     */
    public void deleteProduct(int productId) throws DatabaseException {
        try (ProductDao dao = daoFactory.createProductDao()) {
            dao.delete(productId);
        }
    }

    /**
     * Method to list products of given page
     * @param pageNumber number of current page
     * @param limit limit of products per page
     * @param sort sort way
     * @param direction sort direction
     * @param locale locale name
     * @return list of products
     * @throws DatabaseException if error in sql has occurred
     */
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

    /**
     * Method to list products of given color of given page
     * @param number number of current page
     * @param name name of color
     * @param sort sort way
     * @param direction sort direction
     * @param locale locale name
     * @return list of products
     * @throws DatabaseException if error in sql has occurred
     */
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

    /**
     * Method to list products of given size of given page
     * @param number number of current page
     * @param name name of size
     * @param sort sort way
     * @param direction sort direction
     * @param locale locale name
     * @return list of products
     * @throws DatabaseException if error in sql has occurred
     */
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

    /**
     * Method to list products of given category of given page
     * @param number number of current page
     * @param name name of category
     * @param sort sort way
     * @param direction sort direction
     * @param locale locale name
     * @return list of products
     * @throws DatabaseException if error in sql has occurred
     */
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

    /**
     * Method to count all products
     * @return total number of products
     * @throws DatabaseException if error in sql has occurred
     */
    public int countAllProducts() throws DatabaseException {
        try (ProductDao dao = daoFactory.createProductDao()) {
            return dao.countAllProducts();
        }
    }

    /**
     * Method to count all products of given category
     * @param category category name
     * @return total number of products of category
     * @throws DatabaseException if error in sql has occurred
     */
    public int countProductsByCategory(String category) throws DatabaseException {
        try (ProductDao dao = daoFactory.createProductDao()) {
            return dao.countProductsByCategory(category);
        }
    }

    /**
     * Method to count all products of given color
     * @param color color name
     * @return total number of products of color
     * @throws DatabaseException if error in sql has occurred
     */
    public int countProductsByColor(String color) throws DatabaseException {
        try (ProductDao dao = daoFactory.createProductDao()) {
            return dao.countProductsByColor(color);
        }
    }

    /**
     * Method to count all products of given size
     * @param size size name
     * @return total number of products of size
     * @throws DatabaseException if error in sql has occurred
     */
    public int countProductsBySize(String size) throws DatabaseException {
        try (ProductDao dao = daoFactory.createProductDao()) {
            return dao.countProductsBySize(size);
        }
    }

    /**
     * Method to list all categories
     * @param locale locale name
     * @return list of categories
     * @throws DatabaseException if error in sql has occurred
     */
    public List<Category> listAllCategories(String locale) throws DatabaseException {
        try (ProductDao dao = daoFactory.createProductDao()) {
            return dao.listAllCategories(locale).orElseThrow(
                    () -> new DatabaseException("Could not list categories"));
        }
    }

    /**
     * Method to list all colors
     * @param locale locale name
     * @return list of colors
     * @throws DatabaseException if error in sql has occurred
     */
    public List<Color> listAllColors(String locale) throws DatabaseException {
        try (ProductDao dao = daoFactory.createProductDao()) {
            return dao.listAllColors(locale).orElseThrow(
                    () -> new DatabaseException("Could not list colors"));
        }
    }

    /**
     * Method to list all sizes
     * @param locale locale name
     * @return list of sizes
     * @throws DatabaseException if error in sql has occurred
     */
    public List<Size> listAllSizes(String locale) throws DatabaseException {
        try (ProductDao dao = daoFactory.createProductDao()) {
            return dao.listAllSizes(locale).orElseThrow(
                    () -> new DatabaseException("Could not list sizes"));
        }
    }

    /**
     * Method to get sort direction in SQL style
     * @param direction direction name
     * @return string "ASC" or "DESC"
     */
    public String getSortDirection(String direction) {
        if (direction.equalsIgnoreCase("ascending") || direction.equalsIgnoreCase("По зростанню") ||
                direction.equals("")) {
            return "ASC";
        } else {
            return "DESC";
        }
    }

    /**
     * Method to get product by id in given locale
     * @param id product id
     * @param locale locale name
     * @return product instance
     * @throws DatabaseException if error in sql has occurred
     */
    public Product getProductById(int id, String locale) throws DatabaseException {
        try (ProductDao dao = daoFactory.createProductDao()) {
            return dao.findById(id, locale).orElseThrow(() -> new DatabaseException("Could not get product"));
        }
    }
}
