package com.dataservicios.ttauditbayermercaderismo.repo;

import android.content.Context;

import com.dataservicios.ttauditbayermercaderismo.db.DatabaseHelper;
import com.dataservicios.ttauditbayermercaderismo.db.DatabaseManager;
import com.dataservicios.ttauditbayermercaderismo.model.CategoryProduct;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by jcdia on 22/06/2017.
 */

public class CategoryProductRepo implements Crud {
    private DatabaseHelper helper;

    public CategoryProductRepo(Context context) {

        DatabaseManager.init(context);
        helper = DatabaseManager.getInstance().getHelper();
    }

    @Override
    public int create(Object item) {
        int index = -1;
        CategoryProduct object = (CategoryProduct) item;
        try {
            index = helper.getCategoryProductDao().create(object);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return index;
    }


    @Override
    public int update(Object item) {

        int index = -1;

        CategoryProduct object = (CategoryProduct) item;

        try {
            helper.getCategoryProductDao().update(object);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return index;
    }


    @Override
    public int delete(Object item) {

        int index = -1;

        CategoryProduct object = (CategoryProduct) item;

        try {
            helper.getCategoryProductDao().delete(object);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return index;
    }

    @Override
    public int deleteAll() {

        List<CategoryProduct> items = null;
        int counter = 0;
        try {
            items = helper.getCategoryProductDao().queryForAll();

            for (CategoryProduct object : items) {
                // do something with object
                helper.getCategoryProductDao().deleteById(object.getId());
                counter ++ ;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return counter;
    }


    @Override
    public Object findById(int id) {

        CategoryProduct wishList = null;
        try {
            wishList = helper.getCategoryProductDao().queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wishList;
    }


    @Override
    public List<?> findAll() {

        List<CategoryProduct> items = null;

        try {
            items = helper.getCategoryProductDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;

    }
    @Override
    public Object findFirstReg() {

        Object wishList = null;
        try {
            wishList = helper.getCategoryProductDao().queryBuilder().queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wishList;
    }
    @Override
    public long countReg() {
        long count = 0;
        try {
            count = helper.getCategoryProductDao().countOf();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    /**
     * Busca una lista de Categorias por su company_id
     * @param company_id
     * @return Retorna lista de Products
     */
    public List<CategoryProduct> findByCompanyId(int company_id) {

        List<CategoryProduct> wishList = null;
        try {
            wishList = helper.getCategoryProductDao().queryBuilder().where().eq("company_id",company_id).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wishList;
    }
}