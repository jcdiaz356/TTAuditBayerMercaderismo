package com.dataservicios.ttauditbayermercaderismo.model;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by jcdia on 21/06/2017.
 */

public class Visit {
    @DatabaseField(id = true)
     private int    id;
    @DatabaseField
     private String fullname;
    @DatabaseField
     private String f_start;
    @DatabaseField
     private String f_end;
    @DatabaseField
     private String icon;
    @DatabaseField
     private String company_id;
    @DatabaseField
     private String created_at;
    @DatabaseField
     private String updated_at;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getF_start() {
        return f_start;
    }

    public void setF_start(String f_start) {
        this.f_start = f_start;
    }

    public String getF_end() {
        return f_end;
    }

    public void setF_end(String f_end) {
        this.f_end = f_end;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
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
}
