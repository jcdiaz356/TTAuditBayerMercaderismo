package com.dataservicios.ttauditbayermercaderismo.repo;

import android.content.Context;

import com.dataservicios.ttauditbayermercaderismo.db.DatabaseHelper;
import com.dataservicios.ttauditbayermercaderismo.db.DatabaseManager;
import com.dataservicios.ttauditbayermercaderismo.model.ProductDetail;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by jcdia on 6/06/2017.
 */

public class ProductDetailRepo implements Crud {
    private DatabaseHelper helper;

    public ProductDetailRepo(Context context) {

        DatabaseManager.init(context);
        helper = DatabaseManager.getInstance().getHelper();
    }

    @Override
    public int create(Object item) {
        int index = -1;
        ProductDetail object = (ProductDetail) item;
        try {
            index = helper.getProductDetailDao().create(object);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return index;
    }


    @Override
    public int update(Object item) {

        int index = -1;

        ProductDetail object = (ProductDetail) item;

        try {
            helper.getProductDetailDao().update(object);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return index;
    }


    @Override
    public int delete(Object item) {

        int index = -1;

        ProductDetail object = (ProductDetail) item;

        try {
            helper.getProductDetailDao().delete(object);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return index;
    }

    @Override
    public int deleteAll() {

        List<ProductDetail> items = null;
        int counter = 0;
        try {
            items = helper.getProductDetailDao().queryForAll();

            for (ProductDetail object : items) {
                // do something with object
                helper.getProductDetailDao().deleteById(object.getId());
                counter ++ ;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return counter;
    }


    @Override
    public Object findById(int id) {

        ProductDetail wishList = null;
        try {
            wishList = helper.getProductDetailDao().queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wishList;
    }


    @Override
    public List<?> findAll() {

        List<ProductDetail> items = null;

        try {
            items = helper.getProductDetailDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;

    }

    @Override
    public Object findFirstReg() {

        Object wishList = null;
        try {
            wishList = helper.getProductDetailDao().queryBuilder().queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wishList;
    }

    @Override
    public long countReg() {
        long count = 0;
        try {
            count = helper.getProductDetailDao().countOf();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    /**
     * Busca una lista de ProductDetail por su company_id
     * @param company_id
     * @return Retorna lista de Products
     */
    public List<ProductDetail> findByCompanyId(int company_id) {

        List<ProductDetail> wishList = null;
        try {
            wishList = helper.getProductDetailDao().queryBuilder().where().eq("company_id",company_id).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wishList;
    }

    /**
     * Busca una lista de ProductDetail por su type
     * @param type tipo de tienda
     * @return Retorna lista de Products
     */
    public List<ProductDetail> findByStoreType(String type) {

        List<ProductDetail> wishList = null;
        try {
            wishList = helper.getProductDetailDao().queryBuilder().where().eq("type",type).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wishList;
    }

    /**
     * Busca un producto por su product_id y el type de Store
     * @param product_id
     * @param type El tipo store CADENA, MINICADENA .....
     * @return
     */
    public Object findByProductIdAndType(int product_id,String type) {

        ProductDetail wishList = null;
        try {
            wishList =  helper.getProductDetailDao().queryBuilder().where().eq("product_id",product_id).and().eq("type",type).queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wishList;
    }

}