package com.store.model.service;


import com.store.model.dao.DaoFactory;
import com.store.model.dao.ProductDao;
import com.store.model.entity.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.List;

public class ProductService {
    private static final Logger LOGGER = LogManager.getLogger(ProductService.class);

    DaoFactory daoFactory = DaoFactory.getInstance();

    public List<Product> listAllProducts() {
        try (ProductDao dao = daoFactory.createProductDao()) {
            return dao.findAll();
        }
    }

}
