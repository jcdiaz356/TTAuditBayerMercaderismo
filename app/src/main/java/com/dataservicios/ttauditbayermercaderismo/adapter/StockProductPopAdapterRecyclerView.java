package com.dataservicios.ttauditbayermercaderismo.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dataservicios.ttauditbayermercaderismo.R;
import com.dataservicios.ttauditbayermercaderismo.model.StockProductPop;
import com.dataservicios.ttauditbayermercaderismo.repo.StockProductPopRepo;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

/**
 * Created by jcdia on 23/06/2017.
 */

public class StockProductPopAdapterRecyclerView extends RecyclerView.Adapter<StockProductPopAdapterRecyclerView.StockProductPopViewHolder> {
    private ArrayList<StockProductPop>  stockProductPops;
    private int                         resource;
    private Activity                    activity;
    private int                         store_id;
    private int                         audit_id;
    private StockProductPopRepo         stockProductPopRepo;

    public StockProductPopAdapterRecyclerView(ArrayList<StockProductPop> stockProductPops, int resource, Activity activity, int store_id, int audit_id) {
        this.stockProductPops   = stockProductPops;
        this.resource           = resource;
        this.activity           = activity;
        this.store_id           = store_id;
        this.audit_id           = audit_id;
        stockProductPopRepo     = new StockProductPopRepo(activity);
    }

    @Override
    public StockProductPopViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);

        StockProductPopViewHolder vh = new StockProductPopViewHolder(view, new StockProductPopViewHolder.ITextWatcher() {
            @Override
            public void beforeTextChanged(int position, CharSequence s, int start, int count, int after) {
                // do something
            }

            @Override
            public void onTextChanged(int position, CharSequence s, int start, int before, int count) {

                String stock = s.toString().trim();

                if(stock.length() > 0){
                    stockProductPops.get(position).setStock_encontrado(Integer.valueOf(stock));
                    stockProductPopRepo.update(stockProductPops.get(position));
                }
            }

            @Override
            public void afterTextChanged(int position, Editable s) {
                // do something
            }
        });

       // return new StockProductPopViewHolder(view) ;
        return vh ;
    }

    @Override
    public void onBindViewHolder(final StockProductPopViewHolder holder, int position) {
        final StockProductPop stockProductPop = stockProductPops.get(position);

        holder.tvFullName.setText(stockProductPop.getFullname());
        holder.tvMinimo.setText(String.valueOf(stockProductPop.getMinimo()));
        holder.tvOptimo.setText(String.valueOf(stockProductPop.getOptimo()));
        holder.tvUnidad.setText(stockProductPop.getUnidad());

        holder.imgStatus.setVisibility(View.GONE);
        Picasso.with(activity)
                .load(R.drawable.thumbs_ttaudit)
                .placeholder(R.drawable.loading_image)
                .error(R.drawable.thumbs_ttaudit)
                .into(holder.imgPhoto);

//        holder.etStock.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void onTextChanged(CharSequence s, int st, int b, int c)
//            {
//                String stock = holder.etStock.getText().toString().trim();
//                if(stock.length() > 0){
//                    stockProductPop.setStock_encontrado(Integer.valueOf(String.valueOf(holder.etStock.getText())));
//                    stockProductPopRepo.update(stockProductPop);
//                }
//            }
//            @Override
//            public void beforeTextChanged(CharSequence s, int st, int c, int a)
//            {
//
//            }
//            @Override
//            public void afterTextChanged(Editable s)
//            {
//
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return stockProductPops.size();
    }

    public static class StockProductPopViewHolder extends RecyclerView.ViewHolder {

        private  TextView         tvFullName;
        private  TextView         tvUnidad;
        private  TextView         tvOptimo;
        private  TextView         tvMinimo;
        private  EditText         etStock;
        private  ImageView        imgPhoto;
        private  ImageView        imgStatus;
        private ITextWatcher      mTextWatcher;

        public interface ITextWatcher {
            // you can add/remove methods as you please, maybe you dont need this much
            void beforeTextChanged(int position, CharSequence s, int start, int count, int after);

            void onTextChanged(int position, CharSequence s, int start, int before, int count);

            void afterTextChanged(int position, Editable s);
        }

        public StockProductPopViewHolder(View itemView, ITextWatcher textWatcher) {
            super(itemView);
            tvFullName      = (TextView) itemView.findViewById(R.id.tvFullName);
            tvOptimo        = (TextView) itemView.findViewById(R.id.tvOptimo);
            tvMinimo        = (TextView) itemView.findViewById(R.id.tvMinimo);
            tvUnidad        = (TextView) itemView.findViewById(R.id.tvUnidad);
            etStock         = (EditText)  itemView.findViewById(R.id.etStock);
            imgPhoto        = (ImageView)  itemView.findViewById(R.id.imgPhoto);
            imgStatus       = (ImageView)  itemView.findViewById(R.id.imgStatus);

            mTextWatcher = textWatcher;
            this.etStock.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    mTextWatcher.beforeTextChanged(getAdapterPosition(), s, start, count, after);
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    mTextWatcher.onTextChanged(getAdapterPosition(), s, start, before, count);
                }

                @Override
                public void afterTextChanged(Editable s) {
                    mTextWatcher.afterTextChanged(getAdapterPosition(), s);
                }
            });
        }
    }

    public void setFilter(ArrayList<StockProductPop> stockProductPops){
        this.stockProductPops = new ArrayList<>();
        this.stockProductPops.addAll(stockProductPops);
        notifyDataSetChanged();
    }
}
