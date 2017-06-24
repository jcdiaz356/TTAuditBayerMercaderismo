package com.dataservicios.ttauditbayermercaderismo.repo;

import android.content.Context;

import com.dataservicios.ttauditbayermercaderismo.db.DatabaseHelper;
import com.dataservicios.ttauditbayermercaderismo.db.DatabaseManager;
import com.dataservicios.ttauditbayermercaderismo.model.StockProductPop;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by jcdia on 22/06/2017.
 */

public class StockProductPopRepo  implements Crud {
    private DatabaseHelper helper;

    public StockProductPopRepo(Context context) {

        DatabaseManager.init(context);
        helper = DatabaseManager.getInstance().getHelper();
    }

    @Override
    public int create(Object item) {
        int index = -1;
        StockProductPop object = (StockProductPop) item;
        try {
            index = helper.getStockProductPopDao().create(object);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return index;
    }


    @Override
    public int update(Object item) {

        int index = -1;

        StockProductPop object = (StockProductPop) item;

        try {
            helper.getStockProductPopDao().update(object);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return index;
    }


    @Override
    public int delete(Object item) {

        int index = -1;

        StockProductPop object = (StockProductPop) item;

        try {
            helper.getStockProductPopDao().delete(object);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return index;
    }

    @Override
    public int deleteAll() {

        List<StockProductPop> items = null;
        int counter = 0;
        try {
            items = helper.getStockProductPopDao().queryForAll();

            for (StockProductPop object : items) {
                // do something with object
                helper.getStockProductPopDao().deleteById(object.getId());
                counter ++ ;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return counter;
    }


    @Override
    public Object findById(int id) {

        StockProductPop wishList = null;
        try {
            wishList = helper.getStockProductPopDao().queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wishList;
    }


    @Override
    public List<?> findAll() {
        List<StockProductPop> items = null;
        try {
            items = helper.getStockProductPopDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;

    }

    @Override
    public Object findFirstReg() {

        Object wishList = null;
        try {
            wishList = helper.getStockProductPopDao().queryBuilder().queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wishList;
    }

    @Override
    public long countReg() {
        long count = 0;
        try {
            count = helper.getStockProductPopDao().countOf();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

}