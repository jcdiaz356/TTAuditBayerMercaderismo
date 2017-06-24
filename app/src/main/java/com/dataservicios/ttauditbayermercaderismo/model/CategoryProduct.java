package com.dataservicios.ttauditbayermercaderismo.model;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by jcdia on 22/06/2017.
 */

public class CategoryProduct {

    @DatabaseField(id = true)
    private int     id;
    @DatabaseField
    private int     idpadre;
    @DatabaseField
    private String  fullname;
    @DatabaseField
    private int     type;
    @DatabaseField
    private String  created_at;
    @DatabaseField
    private String  updated_at;
    @DatabaseField
    private int     customer_id;
    @DatabaseField
    private int     company_id;
    @DatabaseField
    private int    status;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdpadre() {
        return idpadre;
    }

    public void setIdpadre(int idpadre) {
        this.idpadre = idpadre;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getCompany_id() {
        return company_id;
    }
    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


}
