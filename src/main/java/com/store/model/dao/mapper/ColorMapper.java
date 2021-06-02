package com.store.model.dao.mapper;

import com.store.model.entity.Category;
import com.store.model.entity.Color;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ColorMapper implements ObjectMapper<Color>{
    @Override
    public Color extractFromResultSet(ResultSet resultSet) throws SQLException {
        Color color = new Color();
        color.setId(resultSet.getInt("id"));
        color.setName(resultSet.getString("name"));
        return color;
    }
}
