package com.store.model.dao.mapper;

import com.store.model.entity.Product;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductMapper implements ObjectMapper<Product>{
    @Override
    public Product extractFromResultSet(ResultSet resultSet) throws SQLException {
        Product product = new Product();
        product.setName(resultSet.getString("name"));
        product.setDescription(resultSet.getString("description"));
        product.setImageLink(resultSet.getString("image_link"));
        product.setPrice(resultSet.getInt("price"));
        product.setCategoryId(resultSet.getLong("category_id"));
        product.setColorId(resultSet.getLong("color_id"));
        product.setSizeId(resultSet.getLong("size_id"));
        return product;
    }
}
