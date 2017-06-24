package com.dataservicios.ttauditbayermercaderismo.model;

/**
 * Created by Jaime on 5/09/2016.
 */
public class PollDetail {
    private int     id;
    private int     poll_id;
    private int     store_id;
    private int     sino;
    private int     options ;
    private int     limits ;
    private int     media;
    private int     comment ;
    private int     result;
    private String  limite ;
    private String  comentario ;
    private int     auditor ;
    private int     product_id;
    private int     category_product_id;
    private int     publicity_id;
    private int     category_id;
    private int     company_id;
    private int     visit_id;
    private int     created_at;
    private int     commentOptions;
    private int     stock_product_pop_id;
    private String  selectdOptions;
    private String  selectedOtionsComment;
    private int     priority ;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPoll_id() {
        return poll_id;
    }

    public void setPoll_id(int poll_id) {
        this.poll_id = poll_id;
    }

    public int getStore_id() {
        return store_id;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }

    public int getSino() {
        return sino;
    }

    public void setSino(int sino) {
        this.sino = sino;
    }

    public int getOptions() {
        return options;
    }

    public void setOptions(int options) {
        this.options = options;
    }

    public int getLimits() {
        return limits;
    }

    public void setLimits(int limits) {
        this.limits = limits;
    }

    public int getMedia() {
        return media;
    }

    public void setMedia(int media) {
        this.media = media;
    }

    public int getComment() {
        return comment;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getLimite() {
        return limite;
    }

    public void setLimite(String limite) {
        this.limite = limite;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public int getAuditor() {
        return auditor;
    }

    public void setAuditor(int auditor) {
        this.auditor = auditor;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getPublicity_id() {
        return publicity_id;
    }

    public void setPublicity_id(int publicity_id) {
        this.publicity_id = publicity_id;
    }

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

    public int getVisit_id() {
        return visit_id;
    }

    public void setVisit_id(int visit_id) {
        this.visit_id = visit_id;
    }

    public int getCategory_product_id() {
        return category_product_id;
    }

    public void setCategory_product_id(int category_product_id) {
        this.category_product_id = category_product_id;
    }

    public int getCreated_at() {
        return created_at;
    }

    public void setCreated_at(int created_at) {
        this.created_at = created_at;
    }

    public int getCommentOptions() {
        return commentOptions;
    }

    public void setCommentOptions(int commentOptions) {
        this.commentOptions = commentOptions;
    }

    public String getSelectdOptions() {
        return selectdOptions;
    }

    public void setSelectdOptions(String selectdOptions) {
        this.selectdOptions = selectdOptions;
    }

    public String getSelectedOtionsComment() {
        return selectedOtionsComment;
    }

    public void setSelectedOtionsComment(String selectedOtionsComment) {
        this.selectedOtionsComment = selectedOtionsComment;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getStock_product_pop_id() {
        return stock_product_pop_id;
    }

    public void setStock_product_pop_id(int stock_product_pop_id) {
        this.stock_product_pop_id = stock_product_pop_id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }
}
