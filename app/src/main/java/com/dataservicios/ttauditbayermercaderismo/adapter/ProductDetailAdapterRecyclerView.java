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

        return new ProductDetailViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(final ProductDetailViewHolder holder, int position) {
        final ProductDetail productDetail = productDetails.get(position);

        holder.tvFullName.setText(productDetail.getFullname());
        holder.tvComposicion.setText(productDetail.getComposicion());
        holder.tvUnidad.setText(productDetail.getUnidad());
        Picasso.with(activity)
                .load(productDetail.getImagen())
                .placeholder(R.drawable.loading_image)
                .error(R.drawable.thumbs_ttaudit)
                .into(holder.imgPhoto);


        holder.etPrecio.addTextChangedListener(new TextWatcher() {

            String afterTextChanged = "";
            String beforeTextChanged = "";
            String onTextChanged = "";

            @Override
            public void onTextChanged(CharSequence s, int st, int b, int c)
            {
                //onTextChanged = holder.etPrecio.getText().toString();
                //holder.etPrecio.getText().toString();
                productDetail.setPrecio(holder.etPrecio.getText().toString());
                productDetailRepo.update(productDetail);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int st, int c, int a)
            {
                beforeTextChanged = holder.etPrecio.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s)
            {

//                Toast.makeText(activity, "before: " + beforeTextChanged
//                                + '\n' + "on: " + onTextChanged
//                                + '\n' + "after: " + afterTextChanged
//                        ,Toast.LENGTH_SHORT).show();
            }
        });
//        if(productDetail.getStatus() == 0){
//            holder.imgStatus.setVisibility(View.INVISIBLE);
//            holder.btAudit.setVisibility(View.VISIBLE);
//        } else {
//            holder.imgStatus.setVisibility(View.VISIBLE);
//            holder.btAudit.setVisibility(View.INVISIBLE);
//        }
//        holder.btAudit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Poll poll = new Poll();
//                poll.setProduct_id(productDetail.getProduct_id());
//                poll.setCategory_product_id(productDetail.getCategory_product_id());
//                poll.setOrder(10);
//                PollProductActivity.createInstance((Activity) activity, store_id,audit_id,poll);
//
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return productDetails.size();
    }

    public class ProductDetailViewHolder extends RecyclerView.ViewHolder {

        private TextView    tvFullName;
        private TextView    tvUnidad;
        private TextView    tvComposicion;
        //private Button      btAudit;
        private EditText    etPrecio;
        private ImageView   imgPhoto;
        private ImageView   imgStatus;

        public ProductDetailViewHolder(View itemView) {
            super(itemView);
            tvFullName      = (TextView) itemView.findViewById(R.id.tvFullName);
            tvComposicion   = (TextView) itemView.findViewById(R.id.tvComposicion);
            tvUnidad        = (TextView) itemView.findViewById(R.id.tvUnidad);
           // btAudit         = (Button)  itemView.findViewById(R.id.btAudit);
            etPrecio        = (EditText)  itemView.findViewById(R.id.etPrecio);
            imgPhoto        = (ImageView)  itemView.findViewById(R.id.imgPhoto);
            imgStatus       = (ImageView)  itemView.findViewById(R.id.imgStatus);
        }
    }

    public void setFilter(ArrayList<ProductDetail> productDetails){
        this.productDetails = new ArrayList<>();
        this.productDetails.addAll(productDetails);
        notifyDataSetChanged();
    }
}
