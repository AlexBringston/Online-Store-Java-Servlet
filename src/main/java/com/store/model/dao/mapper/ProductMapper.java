package com.store.model.dao.mapper;

import com.store.model.entity.Product;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Product mapper. Is used to get data from database and assign it to instance.
 *
 * @author Alexander Mulyk
 * @since 2021-06-14
 */
public class ProductMapper implements ObjectMapper<Product>{
    @Override
    public Product extractFromResultSet(ResultSet resultSet, String locale) throws SQLException {
        Product product = new Product();
        product.setId(resultSet.getInt("id"));
        product.setName(resultSet.getString(String.format("name%s",locale)));
        product.setImageLink(resultSet.getString("image_link"));
        product.setPrice(resultSet.getBigDecimal("price"));
        product.setCategory(resultSet.getString("category"));
        product.setColor(resultSet.getString("color"));
        product.setSize(resultSet.getString("size"));
        product.setCreatedAt(resultSet.getTimestamp("created_at"));
        return product;
    }
}
