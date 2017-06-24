package com.dataservicios.ttauditbayermercaderismo.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dataservicios.ttauditbayermercaderismo.R;
import com.dataservicios.ttauditbayermercaderismo.adapter.CustomItemClickListener;
import com.dataservicios.ttauditbayermercaderismo.adapter.PublicityAdapterReciclerView;
import com.dataservicios.ttauditbayermercaderismo.adapter.PublicityHistoryAdapterReciclerView;
import com.dataservicios.ttauditbayermercaderismo.db.DatabaseManager;
import com.dataservicios.ttauditbayermercaderismo.model.Audit;
import com.dataservicios.ttauditbayermercaderismo.model.Poll;
import com.dataservicios.ttauditbayermercaderismo.model.Publicity;
import com.dataservicios.ttauditbayermercaderismo.model.PublicityHistory;
import com.dataservicios.ttauditbayermercaderismo.model.Store;
import com.dataservicios.ttauditbayermercaderismo.repo.AuditRepo;
import com.dataservicios.ttauditbayermercaderismo.repo.PublicityHistoryRepo;
import com.dataservicios.ttauditbayermercaderismo.repo.PublicityRepo;
import com.dataservicios.ttauditbayermercaderismo.repo.StoreRepo;
import com.dataservicios.ttauditbayermercaderismo.util.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;

public class PublicitiesActivity extends AppCompatActivity {
    private static final String LOG_TAG = PublicitiesActivity.class.getSimpleName();
    private SessionManager                          session;
    private Activity                                activity =  this;
    private int                                     user_id;
    private int                                     store_id;
    private int                                     audit_id;
    private TextView                                tvPublicityHistoryTitle;
    private TextView                                tvTotal;
    private Button                                  btSave;
    private PublicityRepo                           publicityRepo ;
    private PublicityHistoryRepo                    publicityHistoryRepo;
    private AuditRepo                               auditRepo ;
    private StoreRepo                               storeRepo;
    private PublicityAdapterReciclerView            publicityAdapterReciclerView;
    private PublicityHistoryAdapterReciclerView     publicityHistoryAdapterReciclerView;
    private RecyclerView                            publicityRecyclerView;
    private RecyclerView                            publicityHistoryRecyclerView;
    private Publicity                               publicity ;
    private Audit                                   audit ;
    private Store                                   store;
    private PublicityHistory                        publicityHistory;
    private ArrayList<Publicity>                    publicities;
    private ArrayList<PublicityHistory>             publicitiesHistory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicities);

        tvPublicityHistoryTitle = (TextView) findViewById(R.id.tvPublicityHistoryTitle);
        tvTotal                 = (TextView) findViewById(R.id.tvTotal);
        btSave                  = (Button) findViewById(R.id.btSave);



        DatabaseManager.init(this);

        publicityRepo           = new PublicityRepo(activity);
        auditRepo               = new AuditRepo(activity);
        publicityHistoryRepo    = new PublicityHistoryRepo(activity);
        storeRepo               = new StoreRepo(activity);

        Bundle bundle = getIntent().getExtras();
        store_id = bundle.getInt("store_id");
        audit_id = bundle.getInt("audit_id");


        session = new SessionManager(activity);
        HashMap<String, String> userSesion = session.getUserDetails();
        user_id = Integer.valueOf(userSesion.get(SessionManager.KEY_ID_USER)) ;

        audit = (Audit) auditRepo.findById(audit_id);
        store = (Store) storeRepo.findById(store_id);
        showToolbar(audit.getFullname().toString(),false);


        publicityRecyclerView  = (RecyclerView) findViewById(R.id.publicity_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        publicityRecyclerView.setLayoutManager(linearLayoutManager);

        publicities = (ArrayList<Publicity>) publicityRepo.findAll();

        publicityAdapterReciclerView =  new PublicityAdapterReciclerView(publicities, R.layout.cardview_publicity, activity,store_id,audit_id);
        publicityRecyclerView.setAdapter(publicityAdapterReciclerView);

        publicityHistoryRecyclerView = (RecyclerView) findViewById(R.id.publicities_history_recycler_view);
        linearLayoutManager = new LinearLayoutManager(activity);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        publicityHistoryRecyclerView.setLayoutManager(linearLayoutManager);


        publicitiesHistory = (ArrayList<PublicityHistory>) publicityHistoryRepo.findAll();

        if(publicitiesHistory.size()>0){
            tvPublicityHistoryTitle.setText(R.string.text_publicity_history);
        }
        publicityHistoryAdapterReciclerView =  new PublicityHistoryAdapterReciclerView(publicitiesHistory, R.layout.cardview_publicity_history, activity, new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {

                int publicity_history_id = publicitiesHistory.get(position).getId();
                //Toast.makeText(activity,publicity_history_id,Toast.LENGTH_SHORT).show();
                Bundle bundle = new Bundle();
                bundle.putInt("publicity_history_id", publicity_history_id);
                Intent intent = new Intent(activity,PublicityHistoryPhotoActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });
        publicityHistoryRecyclerView.setAdapter(publicityHistoryAdapterReciclerView);

        int total               = publicities.size();
        int publicitiesAudits   = 0;

        for(Publicity p: publicities){
            if(p.getStatus()==1) publicitiesAudits ++;
        }

        tvTotal.setText(String.valueOf(publicitiesAudits) + " de " + String.valueOf(total));

        if(publicities.size() == 0) {
            btSave.setVisibility(View.INVISIBLE);
        }


        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                for (Publicity p:publicities ){
//
//                    if(p.getStatus()==0){
//                        alertDialogBasico(getString(R.string.message_audit_material_pop) + ": \n " + p.getFullname().toString());
//                        return;
//                    }
//                }

                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle(R.string.message_save);
                builder.setMessage(R.string.message_save_information);
                builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {



                        if(store.getStatus_change() ==1) {
                            finish();
                        } else if(store.getStatus_change() == 0) {
                            Poll poll = new Poll();
                            poll.setPublicity_id(0);
                            poll.setOrder(8);
                            PollPublicityActivity.createInstance((Activity) activity, store_id,audit_id,poll);
                            finish();
                        }

                        dialog.dismiss();

                    }
                });
                builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
                builder.setCancelable(false);
            }
        });


    }

    public void showToolbar(String title, boolean upButton){

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onRestart() {
        super.onRestart();
        //When BACK BUTTON is pressed, the activity on the stack is restarted
        //Do what you want on the refresh procedure here
        finish();
        startActivity(getIntent());
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //preventing default implementation previous to android.os.Build.VERSION_CODES.ECLAIR
            //Toast.makeText(MyActivity, "No se puede volver atras, los datos ya fueron guardado, para modificar pongase en contácto con el administrador", Toast.LENGTH_LONG).show();
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    public void onBackPressed() {

//        if(publicities.size() == 0 ) {
//            super.onBackPressed ();
//        } else {
//            for (Publicity p:publicities ){
//
//                if(p.getStatus()==0){
//                    alertDialogBasico(getString(R.string.message_audit_material_pop) + ": \n " + p.getFullname().toString());
//                    return;
//                }
//            }
//            alertDialogBasico(getString(R.string.message_save_audit_material_pop));
//        }

        super.onBackPressed();
    }


    private void alertDialogBasico(String message) {

        // 1. Instancia de AlertDialog.Builder con este constructor
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        // 2. Encadenar varios métodos setter para ajustar las características del diálogo
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                return;
            }
        });
        builder.show();

    }
}
