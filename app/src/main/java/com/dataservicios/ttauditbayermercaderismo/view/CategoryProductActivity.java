package com.dataservicios.ttauditbayermercaderismo.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dataservicios.ttauditbayermercaderismo.R;
import com.dataservicios.ttauditbayermercaderismo.adapter.CategoryProductsAdapterRecyclerView;
import com.dataservicios.ttauditbayermercaderismo.adapter.ProductDetailAdapterRecyclerView;
import com.dataservicios.ttauditbayermercaderismo.db.DatabaseManager;
import com.dataservicios.ttauditbayermercaderismo.model.Audit;
import com.dataservicios.ttauditbayermercaderismo.model.AuditRoadStore;
import com.dataservicios.ttauditbayermercaderismo.model.CategoryProduct;
import com.dataservicios.ttauditbayermercaderismo.model.Company;
import com.dataservicios.ttauditbayermercaderismo.model.ProductDetail;
import com.dataservicios.ttauditbayermercaderismo.model.Route;
import com.dataservicios.ttauditbayermercaderismo.model.Store;
import com.dataservicios.ttauditbayermercaderismo.repo.AuditRepo;
import com.dataservicios.ttauditbayermercaderismo.repo.AuditRoadStoreRepo;
import com.dataservicios.ttauditbayermercaderismo.repo.CategoryProductRepo;
import com.dataservicios.ttauditbayermercaderismo.repo.CompanyRepo;
import com.dataservicios.ttauditbayermercaderismo.repo.RouteRepo;
import com.dataservicios.ttauditbayermercaderismo.repo.StoreRepo;
import com.dataservicios.ttauditbayermercaderismo.util.AuditUtil;
import com.dataservicios.ttauditbayermercaderismo.util.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;

public class CategoryProductActivity extends AppCompatActivity {
    private static final String LOG_TAG = CategoryProductActivity.class.getSimpleName();
    private SessionManager                      session;
    private Activity                            activity =  this;
    private ProgressDialog                      pDialog;
    private int                                 user_id;
    private int                                 store_id;
    private int                                 audit_id;
    private TextView                            tvTotal;
    private Button                              btSave;
    private CategoryProductRepo                 categoryProductRepo;
    private StoreRepo                           storeRepo ;
    private RouteRepo                           routeRepo ;
    private CompanyRepo                         companyRepo ;
    private AuditRepo                           auditRepo ;
    private AuditRoadStoreRepo                  auditRoadStoreRepo ;
    private CategoryProductsAdapterRecyclerView categoryProductsAdapterRecyclerView;
    private RecyclerView                        categoryProductRecyclerView;
    private Audit                               audit ;
    private Company                             company ;
    private Route                               route ;
    private Store                               store ;
    private ArrayList<CategoryProduct>          categoryProducts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_product);

        tvTotal                 = (TextView) findViewById(R.id.tvTotal);
        btSave                  = (Button) findViewById(R.id.btSave);

        DatabaseManager.init(this);

        storeRepo           = new StoreRepo(activity);
        categoryProductRepo = new CategoryProductRepo(activity);
        auditRepo           = new AuditRepo(activity);
        companyRepo         = new CompanyRepo(activity);
        routeRepo           = new RouteRepo(activity);
        auditRoadStoreRepo  = new AuditRoadStoreRepo(activity);

        Bundle bundle = getIntent().getExtras();
        store_id = bundle.getInt("store_id");
        audit_id = bundle.getInt("audit_id");

        company = (Company)companyRepo.findFirstReg();
        store   = (Store) storeRepo.findById(store_id);
        route   = (Route) routeRepo.findById(store.getRoute_id());

        session = new SessionManager(activity);
        HashMap<String, String> userSesion = session.getUserDetails();
        user_id = Integer.valueOf(userSesion.get(SessionManager.KEY_ID_USER)) ;

        audit = (Audit) auditRepo.findById(audit_id);
        showToolbar(audit.getFullname().toString(),false);

        categoryProductRecyclerView  = (RecyclerView) findViewById(R.id.categorias_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        categoryProductRecyclerView.setLayoutManager(linearLayoutManager);

        categoryProducts = (ArrayList<CategoryProduct>) categoryProductRepo.findByCompanyId(company.getId());

        categoryProductsAdapterRecyclerView =  new CategoryProductsAdapterRecyclerView(categoryProducts, R.layout.cardview_category_product, activity,store_id,audit_id);
        categoryProductRecyclerView.setAdapter(categoryProductsAdapterRecyclerView);

        int total               = categoryProducts.size();
        int categoryProductsAudits   = 0;

        for(CategoryProduct m: categoryProducts){
            if(m.getStatus()==1) categoryProductsAudits ++;
        }

        tvTotal.setText(String.valueOf(categoryProductsAudits) + " de " + String.valueOf(total));
        if(categoryProducts.size() == 0) {
            btSave.setVisibility(View.INVISIBLE);
        }
        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle(R.string.message_save);
                builder.setMessage(R.string.message_save_information);
                builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       new savePoll().execute();
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


    class savePoll extends AsyncTask<Void, Integer , Boolean> {
        /**
         * Antes de comenzar en el hilo determinado, Mostrar progresión
         * */
        @Override
        protected void onPreExecute() {
            //tvCargando.setText("Cargando Product...");
            pDialog = new ProgressDialog(activity);
            pDialog.setMessage(getString(R.string.text_loading));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
            super.onPreExecute();

        }
        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO Auto-generated method stub

            if (!AuditUtil.closeAuditStore(audit_id, store_id, company.getId(), route.getId())) return false;

            return true;
        }
        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(Boolean result) {
            // dismiss the dialog once product deleted
            if (result){
                AuditRoadStore auditRoadStore = (AuditRoadStore) auditRoadStoreRepo.findByStoreIdAndAuditId(store_id,audit_id);
                auditRoadStore.setAuditStatus(1);
                auditRoadStoreRepo.update(auditRoadStore);
                finish();
            } else {
                Toast.makeText(activity , R.string.message_no_save_data , Toast.LENGTH_LONG).show();
            }
            pDialog.dismiss();
        }
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
        finish();
        startActivity(getIntent());
    }


    @Override
    public void onBackPressed() {

        if(categoryProducts.size() == 0 ) {
            super.onBackPressed ();
        } else {
            alertDialogBasico(getString(R.string.message_save_audit_products));
        }

        // super.onBackPressed ();
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
