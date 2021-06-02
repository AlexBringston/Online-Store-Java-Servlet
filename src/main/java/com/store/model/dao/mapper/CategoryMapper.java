package com.store.model.dao.mapper;

import com.store.model.entity.Category;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryMapper implements ObjectMapper<Category>{
    @Override
    public Category extractFromResultSet(ResultSet resultSet) throws SQLException {
        Category category = new Category();
        category.setId(resultSet.getInt("id"));
        category.setName(resultSet.getString("name"));
        return category;
    }
}
