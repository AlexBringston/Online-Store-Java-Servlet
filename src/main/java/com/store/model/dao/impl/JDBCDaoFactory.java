package com.store.model.dao.impl;

import com.store.model.dao.DaoFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class JDBCDaoFactory extends DaoFactory {

    private DataSource dataSource = ConnectionPoolHolder.getDataSource();

    @Override
    public JDBCUserDao createUserDao() {
        return new JDBCUserDao(getConnection());
    }

    @Override
    public JDBCProductDao createProductDao() {
        return new JDBCProductDao(getConnection());
    }

    @Override
    public JDBCOrderDao createOrderDao() {
        return new JDBCOrderDao(getConnection());
    }

    @Override
    public JDBCOrderItemDao createOrderItemDao() {
        return new JDBCOrderItemDao(getConnection());
    }

    private Connection getConnection(){
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}