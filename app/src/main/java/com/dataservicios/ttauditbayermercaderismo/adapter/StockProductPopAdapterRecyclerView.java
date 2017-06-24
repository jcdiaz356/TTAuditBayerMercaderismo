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
        return new StockProductPopViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(final StockProductPopViewHolder holder, int position) {
        final StockProductPop stockProductPop = stockProductPops.get(position);

        holder.tvFullName.setText(stockProductPop.getFullname());
        holder.tvMinimo.setText(String.valueOf(stockProductPop.getMinimo()));
        holder.tvOptimo.setText(String.valueOf(stockProductPop.getOptimo()));
        holder.tvUnidad.setText(stockProductPop.getUnidad());

        Picasso.with(activity)
                .load(R.drawable.thumbs_ttaudit)
                .placeholder(R.drawable.loading_image)
                .error(R.drawable.thumbs_ttaudit)
                .into(holder.imgPhoto);

        holder.etStock.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int st, int b, int c)
            {
                if(holder.etStock.getText().length() > 0){
                    stockProductPop.setStock_encontrado(Integer.valueOf(String.valueOf(holder.etStock.getText())));
                    stockProductPopRepo.update(stockProductPop);
                }

            }

            @Override
            public void beforeTextChanged(CharSequence s, int st, int c, int a)
            {

            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });
    }

    @Override
    public int getItemCount() {
        return stockProductPops.size();
    }

    public class StockProductPopViewHolder extends RecyclerView.ViewHolder {

        private TextView    tvFullName;
        private TextView    tvUnidad;
        private TextView    tvOptimo;
        private TextView    tvMinimo;
        private EditText    etStock;
        private ImageView   imgPhoto;
        private ImageView   imgStatus;

        public StockProductPopViewHolder(View itemView) {
            super(itemView);
            tvFullName      = (TextView) itemView.findViewById(R.id.tvFullName);
            tvOptimo        = (TextView) itemView.findViewById(R.id.tvOptimo);
            tvMinimo        = (TextView) itemView.findViewById(R.id.tvMinimo);
            tvUnidad        = (TextView) itemView.findViewById(R.id.tvUnidad);
            etStock         = (EditText)  itemView.findViewById(R.id.etStock);
            imgPhoto        = (ImageView)  itemView.findViewById(R.id.imgPhoto);
            imgStatus       = (ImageView)  itemView.findViewById(R.id.imgStatus);
        }
    }

    public void setFilter(ArrayList<StockProductPop> stockProductPops){
        this.stockProductPops = new ArrayList<>();
        this.stockProductPops.addAll(stockProductPops);
        notifyDataSetChanged();
    }
}
