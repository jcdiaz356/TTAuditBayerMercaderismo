package com.dataservicios.ttauditbayermercaderismo.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dataservicios.ttauditbayermercaderismo.R;
import com.dataservicios.ttauditbayermercaderismo.model.PublicityHistory;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by jcdia on 4/06/2017.
 */

public class PublicityHistoryAdapterReciclerView extends RecyclerView.Adapter<PublicityHistoryAdapterReciclerView.PublicityHistoryViewHolder> {
    private ArrayList<PublicityHistory> publicitiesHistory;
    private int                         resource;
    private Activity                    activity;
    private CustomItemClickListener     listener;
    public PublicityHistoryAdapterReciclerView(ArrayList<PublicityHistory> publicitiesHistory, int resource, Activity activity, CustomItemClickListener listener) {
        this.publicitiesHistory = publicitiesHistory;
        this.resource = resource;
        this.activity = activity;
        this.listener = listener;

    }

    @Override
    public PublicityHistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        final PublicityHistoryViewHolder mViewHolder = new PublicityHistoryViewHolder(view);
        //final PublicityHistory publicityHistory = publicitiesHistory.get(position);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 listener.onItemClick(v, mViewHolder.getPosition());
               // return true;
            }
        });

      //  return new PublicityHistoryViewHolder(view) ;
        return  mViewHolder ;
    }

    @Override
    public void onBindViewHolder(PublicityHistoryViewHolder holder, int position) {
        final PublicityHistory publicityHistory = publicitiesHistory.get(position);

        holder.tvFullName.setText(publicityHistory.getFullname());
        holder.tvCompanyName.setText(publicityHistory.getCompany_name());
        holder.tvDate.setText(publicityHistory.getCreated_at());
        Picasso.with(activity)
                .load(publicityHistory.getImagen())
                .placeholder(R.drawable.loading_image)
                .error(R.drawable.thumbs_ttaudit)
                .into(holder.imgPhoto);

//        holder.setPublicityHistory(publicityHistory);
    }

    @Override
    public int getItemCount() {
        return publicitiesHistory.size();
    }

    public class PublicityHistoryViewHolder extends RecyclerView.ViewHolder  {

        private TextView            tvFullName;
        private TextView            tvCompanyName;
        private TextView            tvDate;
        private ImageView           imgPhoto;

        //private PublicityHistory    publicityHistory;
//        public PublicityHistory getPublicityHistory() {
//            return publicityHistory;
//        }



        public PublicityHistoryViewHolder(View itemView) {
            super(itemView);
            tvFullName      = (TextView) itemView.findViewById(R.id.tvFullName);
            tvCompanyName   = (TextView) itemView.findViewById(R.id.tvCompanyName);
            tvDate          = (TextView) itemView.findViewById(R.id.tvDate);
            imgPhoto        = (ImageView) itemView.findViewById(R.id.imgPhoto);
        }

//        public void setPublicityHistory(PublicityHistory publicityHistory) {
//            this.publicityHistory = publicityHistory;
//        }


//        @Override
//        public void onClick(View v) {
//
//            Toast.makeText(activity,publicityHistory.getCompany_name(),Toast.LENGTH_SHORT).show();
//        }
    }
}
