package com.dataservicios.ttauditbayermercaderismo.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dataservicios.ttauditbayermercaderismo.R;
import com.dataservicios.ttauditbayermercaderismo.model.Poll;
import com.dataservicios.ttauditbayermercaderismo.model.Product;
import com.dataservicios.ttauditbayermercaderismo.view.PollProductActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by jcdia on 6/06/2017.
 */

public class ProductAdapterRecyclerView extends RecyclerView.Adapter<ProductAdapterRecyclerView.ProductViewHolder> {
    private ArrayList<Product>          products;
    private int                         resource;
    private Activity                    activity;
    private int                         store_id;
    private int                         audit_id;

    public ProductAdapterRecyclerView(ArrayList<Product> products, int resource, Activity activity,int store_id, int audit_id) {
        this.products = products;
        this.resource       = resource;
        this.activity       = activity;
        this.store_id       = store_id;
        this.audit_id       = audit_id;

    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);

        return new ProductViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        final Product product = products.get(position);

        holder.tvFullName.setText(product.getFullname());
        holder.tvComposicion.setText(product.getComposicion());
        holder.tvUnidad.setText(product.getUnidad());
        Picasso.with(activity)
                .load(product.getImagen())
                .placeholder(R.drawable.loading_image)
                .error(R.drawable.thumbs_ttaudit)
                .into(holder.imgPhoto);
        if(product.getStatus() == 0){
            holder.imgStatus.setVisibility(View.INVISIBLE);
            holder.btAudit.setVisibility(View.VISIBLE);
        } else {
            holder.imgStatus.setVisibility(View.VISIBLE);
            holder.btAudit.setVisibility(View.INVISIBLE);
        }
        holder.btAudit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Poll poll = new Poll();
                poll.setProduct_id(product.getId());
                poll.setOrder(10);
                PollProductActivity.createInstance((Activity) activity, store_id,audit_id,poll);

            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {

        private TextView    tvFullName;
        private TextView    tvUnidad;
        private TextView    tvComposicion;
        private Button      btAudit;
        private ImageView   imgPhoto;
        private ImageView   imgStatus;

        public ProductViewHolder(View itemView) {
            super(itemView);
            tvFullName      = (TextView) itemView.findViewById(R.id.tvFullName);
            tvComposicion   = (TextView) itemView.findViewById(R.id.tvComposicion);
            tvUnidad        = (TextView) itemView.findViewById(R.id.tvUnidad);
            btAudit         = (Button)  itemView.findViewById(R.id.btAudit);
            imgPhoto        = (ImageView)  itemView.findViewById(R.id.imgPhoto);
            imgStatus       = (ImageView)  itemView.findViewById(R.id.imgStatus);
        }
    }

    public void setFilter(ArrayList<Product> products){
        this.products = new ArrayList<>();
        this.products.addAll(products);
        notifyDataSetChanged();
    }
}
