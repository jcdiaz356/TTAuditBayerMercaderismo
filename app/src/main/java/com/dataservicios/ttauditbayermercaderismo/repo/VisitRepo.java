package com.dataservicios.ttauditbayermercaderismo.repo;

import android.content.Context;

import com.dataservicios.ttauditbayermercaderismo.db.DatabaseHelper;
import com.dataservicios.ttauditbayermercaderismo.db.DatabaseManager;
import com.dataservicios.ttauditbayermercaderismo.model.Visit;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by jcdia on 21/06/2017.
 */

public class VisitRepo implements Crud {

    private DatabaseHelper helper;

    public VisitRepo(Context context) {

        DatabaseManager.init(context);
        helper = DatabaseManager.getInstance().getHelper();
    }

    @Override
    public int create(Object item) {
        int index = -1;
        Visit object = (Visit) item;
        try {
            index = helper.getVisitDao().create(object);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return index;
    }


    @Override
    public int update(Object item) {

        int index = -1;

        Visit object = (Visit) item;

        try {
            helper.getVisitDao().update(object);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return index;
    }


    @Override
    public int delete(Object item) {

        int index = -1;

        Visit object = (Visit) item;

        try {
            helper.getVisitDao().delete(object);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return index;
    }

    @Override
    public int deleteAll() {

        List<Visit> items = null;
        int counter = 0;
        try {
            items = helper.getVisitDao().queryForAll();

            for (Visit object : items) {
                // do something with object
                helper.getVisitDao().deleteById(object.getId());
                counter ++ ;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return counter;
    }


    @Override
    public Object findById(int id) {

        Visit wishList = null;
        try {
            wishList = helper.getVisitDao().queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wishList;
    }


    @Override
    public List<?> findAll() {

        List<Visit> items = null;

        try {
            items = helper.getVisitDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;

    }

    @Override
    public Object findFirstReg() {

        Object wishList = null;
        try {
            wishList = helper.getVisitDao().queryBuilder().queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wishList;
    }

    @Override
    public long countReg() {
        long count = 0;
        try {
            count = helper.getVisitDao().countOf();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }





}