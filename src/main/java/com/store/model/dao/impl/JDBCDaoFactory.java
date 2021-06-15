package com.store.model.dao.impl;

import com.store.model.dao.DaoFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Implemented DAO Factory to create certain Dao objects and pass connection to them.
 *
 * @author Alexander Mulyk
 * @since 2021-06-14
 */
public class JDBCDaoFactory extends DaoFactory {

    /**
     * DataSource instance which was set in Connection Pool Holder.
     */
    private final DataSource dataSource = ConnectionPoolHolder.getDataSource();

    @Override
    public JDBCUserDao createUserDao() {
        return new JDBCUserDao(getConnection());
    }

    @Override
    public JDBCProductDao createProductDao() {
        return new JDBCProductDao(getConnection());
    }

    @Override
    public JDBCDeletedProductDao createDeletedProductDao() {
        return new JDBCDeletedProductDao(getConnection());
    }

    @Override
    public JDBCOrderDao createOrderDao() {
        return new JDBCOrderDao(getConnection());
    }

    @Override
    public JDBCOrderItemDao createOrderItemDao() {
        return new JDBCOrderItemDao(getConnection());
    }

    /**
     * Method to get connection instance from data source which was set before.
     * @return Connection instance
     */
    private Connection getConnection(){
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}