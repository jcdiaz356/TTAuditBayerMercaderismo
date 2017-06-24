package com.dataservicios.ttauditbayermercaderismo.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dataservicios.ttauditbayermercaderismo.R;
import com.dataservicios.ttauditbayermercaderismo.model.CategoryProduct;
import com.dataservicios.ttauditbayermercaderismo.view.CategoryProductActivity;
import com.dataservicios.ttauditbayermercaderismo.view.ProductPriceActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by jcdia on 22/06/2017.
 */

public class CategoryProductsAdapterRecyclerView extends RecyclerView.Adapter<CategoryProductsAdapterRecyclerView.CategoryProductsViewHolder> {
    private ArrayList<CategoryProduct>  categoryProducts;
    private int                         resource;
    private Activity                    activity;
    private int                         store_id;
    private int                         audit_id;

    public CategoryProductsAdapterRecyclerView(ArrayList<CategoryProduct> categoryProducts, int resource, Activity activity, int store_id, int audit_id) {
        this.categoryProducts   = categoryProducts;
        this.resource           = resource;
        this.activity           = activity;
        this.store_id           = store_id;
        this.audit_id           = audit_id;
    }

    @Override
    public CategoryProductsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        return new CategoryProductsViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(CategoryProductsViewHolder holder, int position) {
        final CategoryProduct categoryProduct = categoryProducts.get(position);

        holder.tvFullName.setText(categoryProduct.getFullname());
//        holder.tvComposicion.setText(categoryProduct.getComposicion());
//        holder.tvUnidad.setText(categoryProduct.getUnidad());
        Picasso.with(activity)
                .load("fgh")
                .placeholder(R.drawable.loading_image)
                .error(R.drawable.thumbs_ttaudit)
                .into(holder.imgPhoto);
        if(categoryProduct.getStatus() == 0){
            holder.imgStatus.setVisibility(View.INVISIBLE);
            holder.btAudit.setVisibility(View.VISIBLE);
        } else {
            holder.imgStatus.setVisibility(View.VISIBLE);
            holder.btAudit.setVisibility(View.INVISIBLE);
        }
        holder.btAudit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Bundle bundle = new Bundle();
                bundle.putInt("store_id", Integer.valueOf(store_id));
                bundle.putInt("audit_id", Integer.valueOf(audit_id));
                bundle.putInt("category_product_id", Integer.valueOf(categoryProduct.getId()));
                Intent intent = new Intent(activity,ProductPriceActivity.class);
                intent.putExtras(bundle);
                activity.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryProducts.size();
    }

    public class CategoryProductsViewHolder extends RecyclerView.ViewHolder {

        private TextView    tvFullName;
        private Button      btAudit;
        private ImageView   imgPhoto;
        private ImageView   imgStatus;

        public CategoryProductsViewHolder(View itemView) {
            super(itemView);
            tvFullName      = (TextView) itemView.findViewById(R.id.tvFullName);
            btAudit         = (Button)  itemView.findViewById(R.id.btAudit);
            imgStatus       = (ImageView)  itemView.findViewById(R.id.imgStatus);
            imgPhoto       = (ImageView)  itemView.findViewById(R.id.imgPhoto);
        }
    }

    public void setFilter(ArrayList<CategoryProduct> products){
        this.categoryProducts = new ArrayList<>();
        this.categoryProducts.addAll(products);
        notifyDataSetChanged();
    }
}
