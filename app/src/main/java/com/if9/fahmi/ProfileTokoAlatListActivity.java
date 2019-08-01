package com.if9.fahmi;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.if9.fahmi.Adapter.AdapterDataAlat;
import com.if9.fahmi.Model.ModelDataAlat;
import com.if9.fahmi.Session.SessionManager;
import com.if9.fahmi.Util.ServerApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProfileTokoAlatListActivity extends AppCompatActivity {
    private static final String TAG = ProfileTokoAlatListActivity.class.getSimpleName();
    SessionManager sessionManager;
    String Id_toko;
    Toolbar toolbarProfileTokoAlat;
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    List<ModelDataAlat> modelDataList;
    RecyclerView.LayoutManager manager;
    String id_tko;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_toko_alat_list);
        toolbarProfileTokoAlat = findViewById(R.id.toolbaralatlist);
        setSupportActionBar(toolbarProfileTokoAlat);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("List Alat");

        sessionManager = new SessionManager(this);

        HashMap<String, String> userr = sessionManager.getIdTokoo();
        Id_toko = userr.get(SessionManager.ID_TOKO);


        mRecyclerView = findViewById(R.id.recyclerTempAlat);
        modelDataList = new ArrayList<>();
        //getAlat();
        manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);
        mAdapter = new AdapterDataAlat(ProfileTokoAlatListActivity.this, modelDataList);
        mRecyclerView.setAdapter(mAdapter);


    }

    @Override
    protected void onResume() {
        super.onResume();
        getAlat();
    }

    private void getAlat() {


        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Mengambil...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_GET_TOKO_ALAT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i(TAG, response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String getObject = jsonObject.getString("alat");
                            JSONArray jsonArray = new JSONArray(getObject);
                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject object = jsonArray.getJSONObject(i);
                                ModelDataAlat modelData = new ModelDataAlat();

                                String Nama = object.getString("nama_alat");
                                String Harga = object.getString("harga");
                                String Foto = object.getString("foto");
                                String Id_Alat = object.getString("id_alat");
                                String Stok = object.getString("stok");

                                modelData.setId_alat(Id_Alat);
                                modelData.setNama(Nama);
                                modelData.setHarga(Harga);
                                modelData.setFoto(Foto);
                                modelData.setStok(Stok);
                                modelDataList.add(modelData);


                            }
                            mAdapter.notifyDataSetChanged();
                            progressDialog.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(ProfileTokoAlatListActivity.this, "error reading detail" + e.toString(), Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(ProfileTokoAlatListActivity.this, "error" + error, Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("id_toko", Id_toko);
                return param;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}
