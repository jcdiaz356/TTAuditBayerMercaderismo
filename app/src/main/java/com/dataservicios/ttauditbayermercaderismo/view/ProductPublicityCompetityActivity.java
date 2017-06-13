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
import com.dataservicios.ttauditbayermercaderismo.adapter.PublicityProductCompetityAdapterRecyclerView;
import com.dataservicios.ttauditbayermercaderismo.db.DatabaseManager;
import com.dataservicios.ttauditbayermercaderismo.model.Audit;
import com.dataservicios.ttauditbayermercaderismo.model.Poll;
import com.dataservicios.ttauditbayermercaderismo.model.Product;
import com.dataservicios.ttauditbayermercaderismo.model.Publicity;
import com.dataservicios.ttauditbayermercaderismo.model.PublicityHistory;
import com.dataservicios.ttauditbayermercaderismo.repo.AuditRepo;
import com.dataservicios.ttauditbayermercaderismo.repo.ProductRepo;
import com.dataservicios.ttauditbayermercaderismo.repo.PublicityHistoryRepo;
import com.dataservicios.ttauditbayermercaderismo.repo.PublicityRepo;
import com.dataservicios.ttauditbayermercaderismo.util.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;

public class ProductPublicityCompetityActivity extends AppCompatActivity {
    private static final String LOG_TAG = PublicitiesActivity.class.getSimpleName();
    private SessionManager session;
    private Activity activity =  this;
    private int                                             user_id;
    private int                                             store_id;
    private int                                             audit_id;
    private int                                             product_id;
    private TextView                                        tvTotal;
    private TextView                                        tvProduct;
    private TextView                                        tvManufacture;
    private Button                                          btSave;
    private PublicityRepo                                   publicityRepo ;
    private AuditRepo                                       auditRepo ;
    private ProductRepo                                     productRepo ;
    private PublicityProductCompetityAdapterRecyclerView    publicityProductCompetityAdapterRecyclerView;
    private RecyclerView                                    publicityRecyclerView;
    private Publicity                                       publicity ;
    private Audit                                           audit ;
    private Product                                         product ;
    private ArrayList<Publicity>                            publicities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_publicity_competity);

        tvTotal                 = (TextView) findViewById(R.id.tvTotal);
        btSave                  = (Button) findViewById(R.id.btSave);
        publicityRecyclerView   = (RecyclerView) findViewById(R.id.publicity_recycler_view);
        tvProduct               = (TextView) findViewById(R.id.tvProduct);
        tvProduct               = (TextView) findViewById(R.id.tvProduct);
        tvManufacture           = (TextView) findViewById(R.id.tvManufacture);

        DatabaseManager.init(this);

        publicityRepo           = new PublicityRepo(activity);
        auditRepo               = new AuditRepo(activity);
        productRepo             = new ProductRepo(activity);

        Bundle bundle = getIntent().getExtras();
        store_id    = bundle.getInt("store_id");
        audit_id    = bundle.getInt("audit_id");
        product_id  = bundle.getInt("product_id");


        session = new SessionManager(activity);
        HashMap<String, String> userSesion = session.getUserDetails();
        user_id = Integer.valueOf(userSesion.get(SessionManager.KEY_ID_USER)) ;

        audit   = (Audit) auditRepo.findById(audit_id);
        product = (Product) productRepo.findById(product_id);

        showToolbar(audit.getFullname().toString(),false);
        tvProduct.setText(product.getFullname());
        tvManufacture.setText(product.getFabricante());



        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        publicityRecyclerView.setLayoutManager(linearLayoutManager);

        publicities = (ArrayList<Publicity>) publicityRepo.findAll();

        publicityProductCompetityAdapterRecyclerView =  new PublicityProductCompetityAdapterRecyclerView(publicities, R.layout.cardview_publicity, activity,store_id,audit_id,product_id);
        publicityRecyclerView.setAdapter(publicityProductCompetityAdapterRecyclerView);

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
                for (Publicity p:publicities ){

                    if(p.getStatus()==0){
                        alertDialogBasico(getString(R.string.message_audit_material_pop) + ": \n " + p.getFullname().toString());
                        return;
                    }
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle(R.string.message_save);
                builder.setMessage(R.string.message_save_information);
                builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        product.setStatus(1);
                        productRepo.update(product);
                        finish();
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



        if(publicities.size() == 0 ) {
            super.onBackPressed ();
        } else {
            for (Publicity p:publicities ){

                if(p.getStatus()==0){
                    alertDialogBasico(getString(R.string.message_audit_material_pop) + ": \n " + p.getFullname().toString());
                    return;
                }
            }

            alertDialogBasico(getString(R.string.message_save_audit_material_pop));
        }


        //

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
