package com.store.model.dao;

import com.store.model.dao.impl.JDBCDaoFactory;
import com.store.model.dao.impl.JDBCProductDao;
import com.store.model.dao.impl.JDBCUserDao;

public abstract class DaoFactory {
    private static DaoFactory daoFactory;

    public abstract JDBCUserDao createUserDao();

    public abstract JDBCProductDao createProductDao();

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