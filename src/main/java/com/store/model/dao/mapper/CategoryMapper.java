package com.store.model.dao.mapper;

import com.store.model.entity.Category;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Category mapper. Is used to get data from database and assign it to instance.
 *
 * @author Alexander Mulyk
 * @since 2021-06-14
 */
public class CategoryMapper implements ObjectMapper<Category>{
    @Override
    public Category extractFromResultSet(ResultSet resultSet, String locale) throws SQLException {
        Category category = new Category();
        category.setId(resultSet.getInt("id"));
        category.setName(resultSet.getString(String.format("name%s",locale)));
        return category;
    }
}
