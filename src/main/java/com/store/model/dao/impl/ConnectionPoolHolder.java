package com.store.model.dao.impl;

import org.apache.commons.dbcp.BasicDataSource;

import javax.sql.DataSource;

/**
 * Connection pool holder.
 * It is responsible for establishing data source to be able to connect to database with set parameters.
 *
 * @author Alexander Mulyk
 * @since 2021-06-14
 */
public class ConnectionPoolHolder {
    /**
     * DataSource instance to setup connection
     */
    private static volatile DataSource dataSource;

    /**
     * Method to setup connection if it wasn't before.
     * @return set dataSource instance
     */
    public static DataSource getDataSource(){

        if (dataSource == null){
            synchronized (ConnectionPoolHolder.class) {
                if (dataSource == null) {
                    BasicDataSource ds = new BasicDataSource();
                    ds.setDriverClassName("org.postgresql.Driver");
                    ds.setUrl("jdbc:postgresql://localhost:5432/online_store?useEncoding=true&amp;characterEncoding=UTF-8");
                    ds.setUsername("postgres");
                    ds.setPassword("1111");
                    ds.setMinIdle(5);
                    ds.setMaxIdle(10);
                    ds.setMaxOpenPreparedStatements(100);
                    dataSource = ds;
                }
            }
        }
        return dataSource;

    }

}
