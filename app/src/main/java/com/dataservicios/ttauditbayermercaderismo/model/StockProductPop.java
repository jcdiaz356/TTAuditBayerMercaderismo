package com.dataservicios.ttauditbayermercaderismo.model;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by jcdia on 22/06/2017.
 */

public class StockProductPop {
    @DatabaseField(id = true)
    private int     id;
    @DatabaseField
    private String  fullname;
    @DatabaseField
    private int     publicity_id;
    @DatabaseField
    private int     optimo;
    @DatabaseField
    private int     minimo;
    @DatabaseField
    private String  unidad;
    @DatabaseField
    private String  cadenaRuc;
    @DatabaseField
    private int     company_id;
    @DatabaseField
    private int     vigente;
    @DatabaseField
    private int     stock_encontrado;
    @DatabaseField
    private int     status;
    @DatabaseField
    private String  created_at;
    @DatabaseField
    private String  updated_at;

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

    public int getPublicity_id() {
        return publicity_id;
    }

    public void setPublicity_id(int publicity_id) {
        this.publicity_id = publicity_id;
    }

    public int getOptimo() {
        return optimo;
    }

    public void setOptimo(int optimo) {
        this.optimo = optimo;
    }

    public int getMinimo() {
        return minimo;
    }

    public void setMinimo(int minimo) {
        this.minimo = minimo;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public String getCadenaRuc() {
        return cadenaRuc;
    }

    public void setCadenaRuc(String cadenaRuc) {
        this.cadenaRuc = cadenaRuc;
    }

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

    public int getVigente() {
        return vigente;
    }

    public void setVigente(int vigente) {
        this.vigente = vigente;
    }

    public int getStock_encontrado() {
        return stock_encontrado;
    }

    public void setStock_encontrado(int stock_encontrado) {
        this.stock_encontrado = stock_encontrado;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
