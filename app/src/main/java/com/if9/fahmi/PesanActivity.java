package com.if9.fahmi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.if9.fahmi.Util.ServerApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PesanActivity extends AppCompatActivity {

    Toolbar toolbarPesan;
    EditText Tgl;
    Button sbmt;
    String idUser, idTtoko, Tg, idAlat, namaAlat, hargaAlat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesan);


        toolbarPesan = findViewById(R.id.toolbarpesan);
        setSupportActionBar(toolbarPesan);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tgl = findViewById(R.id.tgl);
        sbmt = findViewById(R.id.submit);

        Intent get = getIntent();
        idUser = get.getStringExtra("id_user");
        idAlat = get.getStringExtra("id_alat");
        idTtoko = get.getStringExtra("id_toko");
        namaAlat = get.getStringExtra("nama_alat");
        hargaAlat = get.getStringExtra("harga_alat");

        sbmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTransaksi();
            }
        });


    }

    private void addTransaksi() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Menyimpan...");
        progressDialog.show();
        StringRequest simpan = new StringRequest(Request.Method.POST, ServerApi.URL_TAMBAH_TRANSAKSI,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject send = new JSONObject(response);
                            if (!send.getBoolean("error")) {
                                Toast.makeText(PesanActivity.this, "error json", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                        }
                        progressDialog.dismiss();
                        Toast.makeText(PesanActivity.this, "berhasil disimpan", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(PesanActivity.this, ProfileTokoAlatListActivity.class));
                        finish();
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(PesanActivity.this, "gagal menambakan", Toast.LENGTH_SHORT).show();
                    }


                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("id", idUser);
                map.put("tgl", Tgl.getText().toString());
                map.put("id_toko", idTtoko);
                map.put("id_alat", idAlat);
                map.put("harga_alat", hargaAlat);
                map.put("nama_alat", namaAlat);


                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(simpan);

    }
}
