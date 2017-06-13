package com.dataservicios.ttauditbayermercaderismo.repo;

import android.content.Context;

import com.dataservicios.ttauditbayermercaderismo.db.DatabaseHelper;
import com.dataservicios.ttauditbayermercaderismo.db.DatabaseManager;
import com.dataservicios.ttauditbayermercaderismo.model.PublicityHistory;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by jcdia on 4/06/2017.
 */

public class PublicityHistoryRepo implements Crud {
    private DatabaseHelper helper;

    public PublicityHistoryRepo(Context context) {

        DatabaseManager.init(context);
        helper = DatabaseManager.getInstance().getHelper();
    }

    @Override
    public int create(Object item) {
        int index = -1;
        PublicityHistory object = (PublicityHistory) item;
        try {
            index = helper.getPublicityHistoryDao().create(object);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return index;
    }


    @Override
    public int update(Object item) {

        int index = -1;

        PublicityHistory object = (PublicityHistory) item;

        try {
            helper.getPublicityHistoryDao().update(object);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return index;
    }


    @Override
    public int delete(Object item) {

        int index = -1;

        PublicityHistory object = (PublicityHistory) item;

        try {
            helper.getPublicityHistoryDao().delete(object);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return index;
    }

    @Override
    public int deleteAll() {

        List<PublicityHistory> items = null;
        int counter = 0;
        try {
            items = helper.getPublicityHistoryDao().queryForAll();

            for (PublicityHistory object : items) {
                // do something with object
                helper.getPublicityHistoryDao().deleteById(object.getId());
                counter ++ ;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return counter;
    }


    @Override
    public Object findById(int id) {

        PublicityHistory wishList = null;
        try {
            wishList = helper.getPublicityHistoryDao().queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wishList;
    }


    @Override
    public List<?> findAll() {

        List<PublicityHistory> items = null;

        try {
            items = helper.getPublicityHistoryDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;

    }

    @Override
    public Object findFirstReg() {

        Object wishList = null;
        try {
            wishList = helper.getPublicityHistoryDao().queryBuilder().queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wishList;
    }

    @Override
    public long countReg() {
        long count = 0;
        try {
            count = helper.getPublicityHistoryDao().countOf();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    /**
     * Busca una lista de PublicityHistory por su company_id
     * @param company_id
     * @return Retorna lista de Products
     */
    public List<PublicityHistory> findByCompanyId(int company_id) {

        List<PublicityHistory> wishList = null;
        try {
            wishList = helper.getPublicityHistoryDao().queryBuilder().where().eq("company_id",company_id).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wishList;
    }

}