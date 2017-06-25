package com.dataservicios.ttauditbayermercaderismo.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dataservicios.ttauditbayermercaderismo.R;
import com.dataservicios.ttauditbayermercaderismo.model.Poll;
import com.dataservicios.ttauditbayermercaderismo.model.ProductDetail;
import com.dataservicios.ttauditbayermercaderismo.repo.ProductDetailRepo;
import com.dataservicios.ttauditbayermercaderismo.repo.ProductRepo;
import com.dataservicios.ttauditbayermercaderismo.view.PollProductActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by jcdia on 6/06/2017.
 */

public class ProductDetailAdapterRecyclerView extends RecyclerView.Adapter<ProductDetailAdapterRecyclerView.ProductDetailViewHolder> {
    private ProductDetailRepo           productDetailRepo;
    private ArrayList<ProductDetail>    productDetails;
    private int                         resource;
    private Activity                    activity;
    private int                         store_id;
    private int                         audit_id;

    public ProductDetailAdapterRecyclerView(ArrayList<ProductDetail> productDetails, int resource, Activity activity, int store_id, int audit_id) {
        productDetailRepo   = new ProductDetailRepo(activity);
        this.productDetails = productDetails;
        this.resource       = resource;
        this.activity       = activity;
        this.store_id       = store_id;
        this.audit_id       = audit_id;

    }

    @Override
    public ProductDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);

        ProductDetailViewHolder vh = new ProductDetailViewHolder(view, new ProductDetailViewHolder.ITextWatcher() {
            @Override
            public void beforeTextChanged(int position, CharSequence s, int start, int count, int after) {
                // do something
            }

            @Override
            public void onTextChanged(int position, CharSequence s, int start, int before, int count) {
                productDetails.get(position).setPrecio(s.toString());
                productDetailRepo.update(productDetails.get(position));
            }

            @Override
            public void afterTextChanged(int position, Editable s) {
                // do something
            }
        });


        //return new ProductDetailViewHolder(view) ;
        return vh ;
    }

    @Override
    public void onBindViewHolder(final ProductDetailViewHolder holder, int position) {
        final ProductDetail productDetail = productDetails.get(position);

        //holder.setIsRecyclable(false);

        holder.tvFullName.setText(productDetail.getFullname());
        holder.tvComposicion.setText(productDetail.getComposicion());
        holder.tvUnidad.setText(productDetail.getUnidad());
        Picasso.with(activity)
                .load(productDetail.getImagen())
                .placeholder(R.drawable.loading_image)
                .error(R.drawable.thumbs_ttaudit)
                .into(holder.imgPhoto);


        holder.imgStatus.setVisibility(View.GONE);
        holder.etPrecio.setText(productDetail.getPrecio());


        //holder.etPrecio.setText(productDetail.getPrecio());

//        holder.etPrecio.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void onTextChanged(CharSequence s, int st, int b, int c)
//            {
//                productDetail.setPrecio(s.toString());
//                productDetailRepo.update(productDetail);
//            }
//            @Override
//            public void beforeTextChanged(CharSequence s, int st, int c, int a)
//            {
//            }
//            @Override
//            public void afterTextChanged(Editable s)
//            {
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return productDetails.size();
    }

    public static class ProductDetailViewHolder extends RecyclerView.ViewHolder {

        private TextView    tvFullName;
        private TextView    tvUnidad;
        private TextView    tvComposicion;
        private EditText    etPrecio;
        private ITextWatcher mTextWatcher;
        private ImageView   imgPhoto;
        private ImageView   imgStatus;

        public interface ITextWatcher {
            // you can add/remove methods as you please, maybe you dont need this much
            void beforeTextChanged(int position, CharSequence s, int start, int count, int after);
            void onTextChanged(int position, CharSequence s, int start, int before, int count);
            void afterTextChanged(int position, Editable s);
        }

        public ProductDetailViewHolder(View itemView, ITextWatcher textWatcher) {
            super(itemView);
            tvFullName      = (TextView) itemView.findViewById(R.id.tvFullName);
            tvComposicion   = (TextView) itemView.findViewById(R.id.tvComposicion);
            tvUnidad        = (TextView) itemView.findViewById(R.id.tvUnidad);
            etPrecio        = (EditText)  itemView.findViewById(R.id.etPrecio);
            imgPhoto        = (ImageView)  itemView.findViewById(R.id.imgPhoto);
            imgStatus       = (ImageView)  itemView.findViewById(R.id.imgStatus);

            mTextWatcher = textWatcher;
            this.etPrecio.addTextChangedListener(new TextWatcher() {
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

    public void setFilter(ArrayList<ProductDetail> productDetails){
        this.productDetails = new ArrayList<>();
        this.productDetails.addAll(productDetails);
        notifyDataSetChanged();
    }
}
