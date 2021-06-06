package com.store.model.dao.mapper;

import com.store.model.exception.DatabaseException;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ObjectMapper<T> {
    T extractFromResultSet(ResultSet resultSet, String locale) throws SQLException, DatabaseException;
}
