package com.store.model.dao.mapper;

import com.store.model.entity.Color;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Color mapper. Is used to get data from database and assign it to instance.
 *
 * @author Alexander Mulyk
 * @since 2021-06-14
 */
public class ColorMapper implements ObjectMapper<Color>{
    @Override
    public Color extractFromResultSet(ResultSet resultSet, String locale) throws SQLException {
        Color color = new Color();
        color.setId(resultSet.getInt("id"));
        color.setName(resultSet.getString(String.format("name%s",locale)));
        return color;
    }
}
