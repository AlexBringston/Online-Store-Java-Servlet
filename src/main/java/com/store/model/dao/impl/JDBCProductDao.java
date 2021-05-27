package com.store.model.dao.impl;

import com.store.model.dao.ProductDao;
import com.store.model.dao.mapper.ProductMapper;
import com.store.model.dao.mapper.UserMapper;
import com.store.model.entity.Product;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JDBCProductDao implements ProductDao {

    private Connection connection;

    public JDBCProductDao(Connection connection) {
        this.connection = connection;
    }


    @Override
    public void create(Product entity) {

    }

    @Override
    public Product findById(int id) {
        return null;
    }

    @Override
    public List<Product> findAll() {
        List<Product> productList = new ArrayList<>();
        Statement statement = null;
        ResultSet rs = null;
        try {
            ProductMapper mapper = new ProductMapper();
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT * FROM products");
            if (rs.next())
                productList.add(mapper.extractFromResultSet(rs));
            rs.close();
            statement.close();
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            ex.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return productList;
    }

    @Override
    public void update(Product entity) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void close() {

    }
}
