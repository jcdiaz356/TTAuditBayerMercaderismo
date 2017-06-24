package com.dataservicios.ttauditbayermercaderismo.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dataservicios.ttauditbayermercaderismo.R;
import com.dataservicios.ttauditbayermercaderismo.adapter.StockProductPopAdapterRecyclerView;
import com.dataservicios.ttauditbayermercaderismo.db.DatabaseManager;
import com.dataservicios.ttauditbayermercaderismo.model.Audit;
import com.dataservicios.ttauditbayermercaderismo.model.AuditRoadStore;
import com.dataservicios.ttauditbayermercaderismo.model.Company;
import com.dataservicios.ttauditbayermercaderismo.model.Poll;
import com.dataservicios.ttauditbayermercaderismo.model.PollDetail;
import com.dataservicios.ttauditbayermercaderismo.model.Publicity;
import com.dataservicios.ttauditbayermercaderismo.model.StockProductPop;
import com.dataservicios.ttauditbayermercaderismo.model.Route;
import com.dataservicios.ttauditbayermercaderismo.model.Store;
import com.dataservicios.ttauditbayermercaderismo.repo.AuditRepo;
import com.dataservicios.ttauditbayermercaderismo.repo.AuditRoadStoreRepo;
import com.dataservicios.ttauditbayermercaderismo.repo.CompanyRepo;
import com.dataservicios.ttauditbayermercaderismo.repo.PollRepo;
import com.dataservicios.ttauditbayermercaderismo.repo.PublicityRepo;
import com.dataservicios.ttauditbayermercaderismo.repo.RouteRepo;
import com.dataservicios.ttauditbayermercaderismo.repo.StockProductPopRepo;
import com.dataservicios.ttauditbayermercaderismo.repo.StoreRepo;
import com.dataservicios.ttauditbayermercaderismo.util.AuditUtil;
import com.dataservicios.ttauditbayermercaderismo.util.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;

public class StockProductPopActivity extends AppCompatActivity {
    private static final String LOG_TAG = StockProductPopActivity.class.getSimpleName();
    private SessionManager                          session;
    private Activity                                activity =  this;
    private ProgressDialog                          pDialog;
    private int                                     user_id;
    private int                                     store_id;
    private int                                     audit_id;
    private int                                     publicity_id;
    private TextView                                tvTotal;
    private Button                                  btSave;
    private StockProductPopRepo                     stockProductPopRepo;
    private StoreRepo                               storeRepo ;
    private RouteRepo                               routeRepo ;
    private CompanyRepo                             companyRepo ;
    private AuditRepo                               auditRepo ;
    private PollRepo                                pollRepo;
    private AuditRoadStoreRepo                      auditRoadStoreRepo ;
    private PublicityRepo                           publicityRepo;
    private StockProductPopAdapterRecyclerView      stockProductPopAdapterRecyclerView;
    private RecyclerView                            pstockProductPopRecyclerView;
    private Audit                                   audit ;
    private StockProductPop                         stockProductPop;
    private Company                                 company ;
    private Route                                   route ;
    private Store                                   store ;
    private AuditRoadStore                          auditRoadStore;
    private Poll                                    poll;
    private PollDetail                              pollDetail;
    private Publicity                               publicity;
    private ArrayList<StockProductPop>              stockProductPops;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_product_pop);
        tvTotal                 = (TextView) findViewById(R.id.tvTotal);
        btSave                  = (Button) findViewById(R.id.btSave);

        DatabaseManager.init(this);

        storeRepo           = new StoreRepo(activity);
        stockProductPopRepo = new StockProductPopRepo(activity);
        auditRepo           = new AuditRepo(activity);
        companyRepo         = new CompanyRepo(activity);
        routeRepo           = new RouteRepo(activity);
        auditRoadStoreRepo  = new AuditRoadStoreRepo(activity);
        pollRepo            = new PollRepo(activity);
        publicityRepo       = new PublicityRepo(activity);


        Bundle bundle = getIntent().getExtras();
        store_id            = bundle.getInt("store_id");
        audit_id            = bundle.getInt("audit_id");
        publicity_id        = bundle.getInt("publicity_id");


        company             = (Company)companyRepo.findFirstReg();
        store               = (Store) storeRepo.findById(store_id);
        route               = (Route) routeRepo.findById(store.getRoute_id());
        auditRoadStore      = (AuditRoadStore)  auditRoadStoreRepo.findByStoreIdAndAuditId(store_id,audit_id);
        poll                = (Poll)            pollRepo.findByCompanyAuditIdAndOrder(auditRoadStore.getList().getCompany_audit_id(),13);
        publicity           = (Publicity)       publicityRepo.findById(publicity_id);


        session = new SessionManager(activity);
        HashMap<String, String> userSesion = session.getUserDetails();
        user_id = Integer.valueOf(userSesion.get(SessionManager.KEY_ID_USER)) ;

        audit = (Audit) auditRepo.findById(audit_id);
        showToolbar(audit.getFullname().toString(),false);

        pstockProductPopRecyclerView = (RecyclerView) findViewById(R.id.stock_product_sood__recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        pstockProductPopRecyclerView.setLayoutManager(linearLayoutManager);

        stockProductPops = (ArrayList<StockProductPop>) stockProductPopRepo.findAll();

        stockProductPopAdapterRecyclerView =  new StockProductPopAdapterRecyclerView(stockProductPops, R.layout.cardview_stock_product_pop, activity,store_id,audit_id);
        pstockProductPopRecyclerView.setAdapter(stockProductPopAdapterRecyclerView);

        int total               = stockProductPops.size();
        int productsAudits   = 0;

        for(StockProductPop p: stockProductPops){
            if(p.getStatus()==1) productsAudits ++;
        }

        tvTotal.setText(String.valueOf(productsAudits) + " de " + String.valueOf(total));
        if(stockProductPops.size() == 0) {
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

    private ArrayList<StockProductPop> filter(ArrayList<StockProductPop> models, String query) {

        query = query.toLowerCase();
        final ArrayList<StockProductPop> filteredModelList = new ArrayList<>();
        for (StockProductPop s : models) {
            final String fullName = s.getFullname().toLowerCase().trim();
            if (fullName.contains(query) ) {
                filteredModelList.add(s);
            }
        }
        return filteredModelList;
    }

    public void showToolbar(String title, boolean upButton){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //storeAdapterRecyclerView.getFilter().filter(newText.toString());
                final ArrayList<StockProductPop> filteredMStoreList = filter(stockProductPops, newText);
                //adapter.setFilter(filteredModelList);
                stockProductPopAdapterRecyclerView.setFilter(filteredMStoreList);
                return false;
            }
        });
        return true;
    }

    class savePoll extends AsyncTask<Void, Integer , Boolean> {
        /**
         * Antes de comenzar en el hilo determinado, Mostrar progresión
         * */
        @Override
        protected void onPreExecute() {
            //tvCargando.setText("Cargando StockProductPop...");
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


            pollDetail = new PollDetail();
            pollDetail.setPoll_id(poll.getId());
            pollDetail.setStore_id(store_id);
            pollDetail.setSino(poll.getSino());
            pollDetail.setOptions(poll.getOptions());
            pollDetail.setLimits(0);
            pollDetail.setMedia(poll.getMedia());
            pollDetail.setComment(1);
            pollDetail.setResult(0);
            pollDetail.setLimite("0");
            //pollDetail.setComentario(comment);
            pollDetail.setAuditor(user_id);
            pollDetail.setProduct_id(poll.getProduct_id());
            pollDetail.setCategory_product_id(poll.getCategory_product_id());
            pollDetail.setProduct_id(poll.getProduct_id());
            pollDetail.setCompany_id(company.getId());
            pollDetail.setCommentOptions(poll.getComment());
            pollDetail.setSelectdOptions("");
            pollDetail.setVisit_id(store.getVisit_id());
            pollDetail.setSelectedOtionsComment("");
            pollDetail.setPriority(0);

            stockProductPops = (ArrayList<StockProductPop>) stockProductPopRepo.findAll();

            for (StockProductPop m: stockProductPops) {
                if(m.getStock_encontrado()>0){
                    pollDetail.setStock_product_pop_id(m.getId());
                    pollDetail.setComentario(String.valueOf(m.getStock_encontrado()));
                    if (!AuditUtil.insertPollDetail(pollDetail)) return false;
                }
            }



            return true;
        }
        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(Boolean result) {
            // dismiss the dialog once stockProductPop deleted
            if (result){
//                AuditRoadStore auditRoadStore = (AuditRoadStore) auditRoadStoreRepo.findByStoreIdAndAuditId(store_id,audit_id);
//                auditRoadStore.setAuditStatus(1);
//                auditRoadStoreRepo.update(auditRoadStore);
                stockProductPops = (ArrayList<StockProductPop>) stockProductPopRepo.findAll();
                for (StockProductPop m: stockProductPops) {
                    m.setStock_encontrado(0);
                    stockProductPopRepo.update(m);
                }
//                categoryProduct.setStatus(1);
//                categoryProductRepo.update(categoryProduct);

                poll.setPublicity_id(publicity_id);
                poll.setOrder(6);
                PollPublicityActivity.createInstance(activity, store_id,audit_id,poll);
                finish();
            } else {
                Toast.makeText(activity , R.string.message_no_save_data , Toast.LENGTH_LONG).show();
            }
            pDialog.dismiss();
        }
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


    //    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            onBackPressed();
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }
    @Override
    public void onBackPressed() {

//        if(stockProductPops.size() == 0 ) {
//            super.onBackPressed ();
//        } else {
        //       alertDialogBasico(getString(R.string.message_save_audit_products));
        // }
        super.onBackPressed ();
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