package com.store.model.dao.mapper;

import com.store.model.entity.Size;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Size mapper. Is used to get data from database and assign it to instance.
 *
 * @author Alexander Mulyk
 * @since 2021-06-14
 */
public class SizeMapper implements ObjectMapper<Size>{
    @Override
    public Size extractFromResultSet(ResultSet resultSet, String locale) throws SQLException {
        Size size = new Size();
        size.setId(resultSet.getInt("id"));
        size.setName(resultSet.getString(String.format("name%s",locale)));
        return size;
    }
}
