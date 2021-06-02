package com.store.model.dao.mapper;

import com.store.model.entity.Size;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SizeMapper implements ObjectMapper<Size>{
    @Override
    public Size extractFromResultSet(ResultSet resultSet) throws SQLException {
        Size size = new Size();
        size.setId(resultSet.getInt("id"));
        size.setName(resultSet.getString("name"));
        return size;
    }
}
