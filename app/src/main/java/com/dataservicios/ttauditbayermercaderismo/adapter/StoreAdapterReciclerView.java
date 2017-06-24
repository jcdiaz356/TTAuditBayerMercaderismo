package com.dataservicios.ttauditbayermercaderismo.adapter;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dataservicios.ttauditbayermercaderismo.R;
import com.dataservicios.ttauditbayermercaderismo.db.DatabaseManager;
import com.dataservicios.ttauditbayermercaderismo.model.Company;
import com.dataservicios.ttauditbayermercaderismo.model.Publicity;
import com.dataservicios.ttauditbayermercaderismo.model.RouteStoreTime;
import com.dataservicios.ttauditbayermercaderismo.model.Store;
import com.dataservicios.ttauditbayermercaderismo.model.User;
import com.dataservicios.ttauditbayermercaderismo.repo.CompanyRepo;
import com.dataservicios.ttauditbayermercaderismo.repo.PublicityRepo;
import com.dataservicios.ttauditbayermercaderismo.repo.RouteStoreTimeRepo;
import com.dataservicios.ttauditbayermercaderismo.repo.StoreRepo;
import com.dataservicios.ttauditbayermercaderismo.repo.UserRepo;
import com.dataservicios.ttauditbayermercaderismo.util.GPSTracker;
import com.dataservicios.ttauditbayermercaderismo.view.PublicitiesActivity;
import com.dataservicios.ttauditbayermercaderismo.view.StoreAuditActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by jcdia on 12/05/2017.
 */

public class StoreAdapterReciclerView extends RecyclerView.Adapter<StoreAdapterReciclerView.StoreViewHolder>  {

    private ArrayList<Store>    stores;
    private int                 resource;
    private Activity            activity;
    private Filter              fRecords;
    private GPSTracker          gpsTracker;
    private User                user;
    private Company             company;
    private StoreRepo           storeRepo;


    public StoreAdapterReciclerView(ArrayList<Store> stores, int resource, Activity activity) {
        this.stores     = stores;
        this.resource   = resource;
        this.activity   = activity;

        gpsTracker = new GPSTracker(activity);
        if(!gpsTracker.canGetLocation()){
            gpsTracker.showSettingsAlert();
        }

        DatabaseManager.init(activity);
        UserRepo    userRepo    = new UserRepo(activity);
        CompanyRepo company_repo= new CompanyRepo(activity);
        storeRepo               = new StoreRepo(activity);

        user    = (User)    userRepo.findFirstReg();
        company = (Company) company_repo.findFirstReg();

    }

    @Override
    public StoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        view.setOnClickListener(new  View.OnClickListener(){
            public void onClick(View v)
            {
                //action
                //Toast.makeText(activity,"dfgdfg",Toast.LENGTH_SHORT).show();
            }
        });

        return new StoreAdapterReciclerView.StoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StoreViewHolder holder, int position) {
        final Store store = stores.get(position);

        holder.tvId.setText("ID : " + String.valueOf(store.getId()));
        holder.tvFullName.setText(store.getFullname());
        holder.tvAddress.setText(String.valueOf(store.getAddress()));
        holder.tvDistrict.setText(String.valueOf(store.getDistrict()));
        holder.tvType.setText(String.valueOf(store.getType() + " (" + store.getCadenRuc()+ ")"));

        if(store.getStatus() >= 1)  {
            holder.btAudit.setVisibility(View.GONE);
            holder.imgStatus.setVisibility(View.VISIBLE) ;
            holder.btChangePop.setVisibility(View.VISIBLE);
        } else {
            holder.btAudit.setVisibility(View.VISIBLE) ;
            holder.imgStatus.setVisibility(View.INVISIBLE);
            holder.btChangePop.setVisibility(View.GONE);

        }

        holder.btShared.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "ID Store: " + store.getId() + " \nTienda: " + store.getFullname()  ;
                String shareSub = "Ruta";
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                sharingIntent.putExtra(Intent.EXTRA_TITLE, shareBody);
                activity.startActivity(Intent.createChooser(sharingIntent, "Share using"));

                store.setStatus_change(0);
                storeRepo.update(store);

            }
        });

        holder.btAudit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                double lat = gpsTracker.getLatitude();
                double lon = gpsTracker.getLatitude();

                if(gpsTracker.canGetLocation()){
                    lat = gpsTracker.getLatitude();
                    lon = gpsTracker.getLatitude();
                }

                String created_at = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss").format(new Date());
                RouteStoreTime routeStoreTime = new RouteStoreTime();
                routeStoreTime.setCompany_id(company.getId());
                routeStoreTime.setStore_id(store.getId());
                routeStoreTime.setRoute_id(store.getRoute_id());
                routeStoreTime.setUser_id(user.getId());
                routeStoreTime.setLat_open(lat);
                routeStoreTime.setLon_open(lon);
                routeStoreTime.setTime_open(created_at);

                RouteStoreTimeRepo routeStoreTimeRepo = new RouteStoreTimeRepo(activity);
                routeStoreTimeRepo.deleteAll();
                routeStoreTimeRepo.create(routeStoreTime);

                int store_id = store.getId();
//              Toast.makeText(activity, String.valueOf(store.id), Toast.LENGTH_SHORT).show();
                Bundle bundle = new Bundle();
                bundle.putInt("store_id", Integer.valueOf(store_id));
                Intent intent = new Intent(activity,StoreAuditActivity.class);
                intent.putExtras(bundle);
                activity.startActivity(intent);

            }
        });

        holder.btChangePop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PublicityRepo publicityRepo = new PublicityRepo(activity);
                ArrayList<Publicity> publicities = (ArrayList<Publicity>) publicityRepo.findAll();

                for (Publicity p: publicities){
                    p.setStatus(0);
                    publicityRepo.update(p);
                }

                store.setStatus_change(1);
                storeRepo.update(store);

                Bundle bundle = new Bundle();
                bundle.putInt("store_id", Integer.valueOf(store.getId()));
                bundle.putInt("audit_id", Integer.valueOf(56));

                Intent intent = new Intent(activity,PublicitiesActivity.class);
                intent.putExtras(bundle);
                activity.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {

        return stores.size();
    }

//    @Override
//    public Filter getFilter() {
//        if(fRecords == null) {
//            fRecords=new RecordFilter();
//        }
//        return fRecords;
//    }
//
    public class StoreViewHolder extends RecyclerView.ViewHolder {
        private TextView    tvId;
        private TextView    tvFullName;
        private TextView    tvAddress;
        private TextView    tvDistrict;
        private TextView    tvType;
        private Button      btShared;
        private Button      btAudit;
        private Button      btChangePop;
        private ImageView   imgStatus;

        public StoreViewHolder(View itemView) {
            super(itemView);
            tvId            = (TextView)    itemView.findViewById(R.id.tvId);
            tvFullName      = (TextView)    itemView.findViewById(R.id.tvFullName);
            tvAddress       = (TextView)    itemView.findViewById(R.id.tvAddress);
            tvDistrict      = (TextView)    itemView.findViewById(R.id.tvDistrict);
            tvType          = (TextView)    itemView.findViewById(R.id.tvType);
            btShared        = (Button)      itemView.findViewById(R.id.btShared);
            btAudit         = (Button)      itemView.findViewById(R.id.btAudit);
            btChangePop     = (Button)      itemView.findViewById(R.id.btChangePop);
            imgStatus       = (ImageView)   itemView.findViewById(R.id.imgStatus);
        }
    }

//    private class RecordFilter extends Filter {
//
//
//        @Override
//        protected FilterResults performFiltering(CharSequence constraint) {
//            FilterResults results = new FilterResults();
//
//            //Implement filter logic
//            // if edittext is null return the actual list
//            if (constraint == null || constraint.length() == 0) {
//                //No need for filter
//                results.values = stores;
//                results.count = stores.size();
//
//            } else {
//                //Need Filter
//                // it matches the text  entered in the edittext and set the data in adapter list
//                ArrayList<Store> fRecords = new ArrayList<Store>();
//
//                for (Store s : stores) {
//                    if (s.getFullname().toUpperCase().trim().contains(constraint.toString().toUpperCase().trim())) {
//                        //fRecords.add(s);
//                        fRecords.add(s);
//                    }
//                }
//                results.values = fRecords;
//                results.count = fRecords.size();
//            }
//            return results;
//        }
//
//        @Override
//        protected void publishResults(CharSequence constraint, FilterResults results) {
//            stores = (ArrayList<Store>) results.values;
//            notifyDataSetChanged();
//     }
//    }

    public void setFilter(ArrayList<Store> stores){
        this.stores = new ArrayList<>();
        this.stores.addAll(stores);
        notifyDataSetChanged();
    }
}
