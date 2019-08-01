package com.if9.fahmi;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.if9.fahmi.Session.SessionManager;
import com.if9.fahmi.Util.ServerApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

//https://badoystudio.com/mengatur-title-subtitle-dan-logo-toolbar-aplikasi-android/ || custom title bar
//http://ugiksetyawan.blogspot.com/2018/02/membuat-menu-navigation-drawer-di.html  | navigasi dengan item click
public class HomeActivity extends AppCompatActivity {
    private static final String TAG = HomeActivity.class.getSimpleName();
    String nama, alamat, email, username, password, nik, telp, jk, foto, grup, getId;
    TextView tvnama, tvalamat, tvemail, tvusername, tvpass, tvnik, tvtelp, tvjk, tvfoto, tvgrup, namaHeader, emailHeader;
    Button btnLogout;
    Intent cek;
    SessionManager sessionManager;
    String cekUser;
    Toolbar toolbar;
    LinearLayout fiturCari, fiturTransaksi, fiturRiwayat, fiturTrending;
    CircleImageView imgProfile;
    Bitmap bitmap;
    private String key_nama = "NAMA";
    private String key_alamat = "ALAMAT";
    private String key_email = "EMAIL";
    private String key_username = "USERNAME";
    private String key_password = "PASSWORD";
    private String key_nik = "NIK";
    private String key_telp = "TELP";
    private String key_jk = "JK";
    private String key_foto = "FOTO";
    private String key_grup_user = "GRUP_USER";
    private int waktu = 5000;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = findViewById(R.id.tolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");


        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();

        mDrawerLayout = findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Animation animAlpha = AnimationUtils.loadAnimation(this, R.anim.anim_alpha);


        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);






        /*Intent extra = getIntent();

        nama=extra.getStringExtra(key_nama);
        alamat=extra.getStringExtra(key_alamat);
        email=extra.getStringExtra(key_email);
        username=extra.getStringExtra(key_username);
        password=extra.getStringExtra(key_password);
        nik=extra.getStringExtra(key_nik);
        telp=extra.getStringExtra(key_telp);
        jk=extra.getStringExtra(key_jk);
        foto=extra.getStringExtra(key_foto);
        grup=extra.getStringExtra(key_grup_user);
*/
        tvnama = findViewById(R.id.edtnama);
        tvalamat = findViewById(R.id.edtalamat);
        tvemail = findViewById(R.id.edtemail);
        tvusername = findViewById(R.id.edtusername);
        tvpass = findViewById(R.id.edtpassword);
        tvnik = findViewById(R.id.edtnik);
        tvtelp = findViewById(R.id.edttelp);
        tvjk = findViewById(R.id.edtjk);
        tvfoto = findViewById(R.id.edtfoto);
        tvgrup = findViewById(R.id.edtgrup);
        btnLogout = findViewById(R.id.logout);

        fiturCari = findViewById(R.id.fiturcari);
        fiturRiwayat = findViewById(R.id.fiturriwayat);
        fiturTransaksi = findViewById(R.id.fiturtransaksi);
        fiturTrending = findViewById(R.id.fiturtrending);

        namaHeader = headerView.findViewById(R.id.namaheader);
        emailHeader = headerView.findViewById(R.id.emailheader);
        imgProfile = headerView.findViewById(R.id.fotop);

        // tvnama.setText(nama);
        //   tvalamat.setText(alamat);
        //  tvemail.setText(email);
        //   tvusername.setText(username);
        //   tvpass.setText(password);
     /*   tvnik.setText(nik);
        tvtelp.setText(telp);
        tvjk.setText(jk);
        tvfoto.setText(foto);
        tvgrup.setText(grup);*/

        HashMap<String, String> userr = sessionManager.getUserDetail();
        String mNama = userr.get(SessionManager.NAMA);
        String mEmail = userr.get(SessionManager.EMAIL);
        String mAlamat = userr.get(SessionManager.ALAMAT);
        String mUsername = userr.get(SessionManager.USERNAME);
        String mPassword = userr.get(SessionManager.PASSWORD);
        String mNIK = userr.get(SessionManager.NIK);
        String mTelp = userr.get(SessionManager.TELP);
        String mjk = userr.get(SessionManager.JENIS_KELAMIN);
        String mFoto = userr.get(SessionManager.FOTO);
        String mGrupUser = userr.get(SessionManager.GRUP_USER);
        getId = userr.get(SessionManager.ID);

        tvnama.setText(getId);
        tvalamat.setText(mAlamat);
        tvemail.setText(mEmail);
        tvusername.setText(mUsername);
        tvpass.setText(mPassword);
        tvnik.setText(mNIK);
        tvtelp.setText(mTelp);
        tvjk.setText(mjk);
        tvfoto.setText(mFoto);
        tvgrup.setText(mGrupUser);

        namaHeader.setText(mNama);
        emailHeader.setText(mEmail);
        new GetImageFromURL(imgProfile).execute(mFoto);


        cekUser = mGrupUser;
       /* if(tvgrup.getText().toString().equals("admin")){
            cek= new Intent(HomeActivity.this, HomeAdminActivity.class);

        }else if (tvgrup.getText().toString().equals("customer")){
            cek = new Intent(HomeActivity.this, HomeUserActivity.class);
            //finish();
             }

        new Handler().postDelayed(new Runnable() {


                @Override
                public void run() {
                    // check();
                    startActivity(cek);
                    finish();


                }
            },waktu);
*/


        fiturCari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animAlpha);
                Intent gotoCari = new Intent(HomeActivity.this, CariActivity.class);
                startActivity(gotoCari);
            }
        });
        fiturRiwayat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animAlpha);
                Intent gotoRiwayat = new Intent(HomeActivity.this, RiwayatActivity.class);
                startActivity(gotoRiwayat);
            }
        });
        fiturTransaksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animAlpha);
                Intent gotoTransaksi = new Intent(HomeActivity.this, TransaksiActivity.class);
                startActivity(gotoTransaksi);
            }
        });
        fiturTrending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animAlpha);
                Intent gotoTrend = new Intent(HomeActivity.this, TrendingActivity.class);
                startActivity(gotoTrend);
            }
        });
        //saat item navigasi di klik
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else
                    menuItem.setChecked(true);

                switch (menuItem.getItemId()) {
                    case R.id.profile:
                        Intent gotoProf = new Intent(HomeActivity.this, ProfileActivity.class);
                        startActivity(gotoProf);
                        return true;
                    case R.id.seting:
                        Intent gotoSetting = new Intent(HomeActivity.this, PengaturanActivity.class);
                        startActivity(gotoSetting);
                        return true;
                    case R.id.favorite:
                        Intent gotoFav = new Intent(HomeActivity.this, FavoriteActivity.class);
                        startActivity(gotoFav);
                        return true;
                    case R.id.logout:
                        sessionManager.logout();
                        return true;
                }

                return true;
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getUserDetail() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_READ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i(TAG, response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("read");
                            String succes = jsonObject.getString("success");
                            if (succes.equals("1")) {
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String Nama = object.getString("nama");
                                    String Alamat = object.getString("alamat");
                                    String Email = object.getString("email");
                                    String Username = object.getString("username");
                                    String Password = object.getString("password");
                                    String NIK = object.getString("nik");
                                    String Telp = object.getString("telp");
                                    String JenisKelamin = object.getString("jenis_kelamin");
                                    String Foto = object.getString("foto");
                                    String GrupUser = object.getString("grup_user");
                                    String Id = object.getString("id");


                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(HomeActivity.this, "error reading detail" + e.toString(), Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(HomeActivity.this, "error" + error, Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("id", getId);
                return param;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    @Override
    protected void onResume() {
        super.onResume();
        //getUserDetail();
    }

    public class GetImageFromURL extends AsyncTask<String, Void, Bitmap> {
        ImageView imgV;

        public GetImageFromURL(ImageView imgV) {
            this.imgV = imgV;
        }

        @Override
        protected Bitmap doInBackground(String... url) {
            String urldisplay = url[0];
            imgProfile = null;
            try {
                InputStream srt = new java.net.URL(urldisplay).openStream();
                bitmap = BitmapFactory.decodeStream(srt);

            } catch (EOFException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            imgV.setImageBitmap(bitmap);
        }
    }
}

