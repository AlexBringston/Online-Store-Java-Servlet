package com.store.model.dao;

import com.store.model.dao.impl.*;

public abstract class DaoFactory {
    private static DaoFactory daoFactory;

    public abstract JDBCUserDao createUserDao();

    public abstract JDBCProductDao createProductDao();

    public abstract JDBCDeletedProductDao createDeletedProductDao();

    public abstract JDBCOrderDao createOrderDao();

    public abstract JDBCOrderItemDao createOrderItemDao();

    public static DaoFactory getInstance(){
        if( daoFactory == null ){
            synchronized (DaoFactory.class){
                if(daoFactory==null){
                    DaoFactory temp = new JDBCDaoFactory();
                    daoFactory = temp;
                }
            }
        }
        return daoFactory;
    }
}