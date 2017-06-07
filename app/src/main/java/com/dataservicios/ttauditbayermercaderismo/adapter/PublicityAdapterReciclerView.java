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
import com.dataservicios.ttauditbayermercaderismo.model.Publicity;
import com.dataservicios.ttauditbayermercaderismo.view.PollPublicityActivity;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;

/**
 * Created by jcdia on 3/06/2017.
 */

public class PublicityAdapterReciclerView extends RecyclerView.Adapter<PublicityAdapterReciclerView.PublicityViewHolder> {
    private ArrayList<Publicity>        publicities;
    private int                         resource;
    private Activity                    activity;
    private int                         store_id;
    private int                         audit_id;

    public PublicityAdapterReciclerView(ArrayList<Publicity> publicities, int resource, Activity activity,int store_id, int audit_id) {
        this.publicities    = publicities;
        this.resource       = resource;
        this.activity       = activity;
        this.store_id       = store_id;
        this.audit_id       = audit_id;

    }

    @Override
    public PublicityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);

        return new PublicityViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(PublicityViewHolder holder, int position) {
        final Publicity publicity = publicities.get(position);


        holder.tvFullName.setText(publicity.getFullname());
        //holder.tvDescription.setText(publicity.getDescription());
        Picasso.with(activity)
                .load(publicity.getImagen())
                .placeholder(R.drawable.loading_image)
                .error(R.drawable.thumbs_ttaudit)
                .into(holder.imgPhoto);
        if(publicity.getStatus() == 0){
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
                poll.setPublicity_id(publicity.getId());
                poll.setOrder(3);
                PollPublicityActivity.createInstance((Activity) activity, store_id,audit_id,poll);

            }
        });
    }

    @Override
    public int getItemCount() {
        return publicities.size();
    }

    public class PublicityViewHolder extends RecyclerView.ViewHolder {

        private TextView    tvFullName;
        //private TextView    tvDescription;
        private Button      btAudit;
        private ImageView   imgPhoto;
        private ImageView   imgStatus;

        public PublicityViewHolder(View itemView) {
            super(itemView);
            tvFullName      = (TextView) itemView.findViewById(R.id.tvFullName);
           // tvDescription   = (TextView) itemView.findViewById(R.id.tvDescription);
            btAudit         = (Button)  itemView.findViewById(R.id.btAudit);
            imgPhoto        = (ImageView)  itemView.findViewById(R.id.imgPhoto);
            imgStatus       = (ImageView)  itemView.findViewById(R.id.imgStatus);
        }
    }
}
