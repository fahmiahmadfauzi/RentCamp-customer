package com.if9.fahmi;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.if9.fahmi.Session.SessionManager;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class PengaturanActivity extends AppCompatActivity {
    Toolbar toolbarPengaturan;
    LinearLayout pAkun, pPass;
    CircleImageView imgP;
    TextView Nama, Email;
    SessionManager sessionManager;
    String getId;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengaturan);
        toolbarPengaturan = findViewById(R.id.toolbarpengaturan);
        setSupportActionBar(toolbarPengaturan);
        getSupportActionBar().setTitle("Pengaturan");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sessionManager = new SessionManager(this);

        pAkun = findViewById(R.id.pengaturanakun);
        pPass = findViewById(R.id.pengaturanpassword);
        imgP = findViewById(R.id.foto_p);
        Nama = findViewById(R.id.nama);
        Email = findViewById(R.id.emaill);

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

        new GetImageFromURL(imgP).execute(mFoto);
        Nama.setText(mNama);
        Email.setText(mEmail);


        final Animation animAlpha = AnimationUtils.loadAnimation(this, R.anim.anim_alpha);

        pAkun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animAlpha);
                Intent gotoAkun = new Intent(PengaturanActivity.this, EditAkunActivity.class);
                startActivity(gotoAkun);

            }
        });
        pPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animAlpha);
                Intent gotoPass = new Intent(PengaturanActivity.this, EditPasswordActivity.class);
                startActivity(gotoPass);
            }
        });
    }

    public class GetImageFromURL extends AsyncTask<String, Void, Bitmap> {
        ImageView imgV;

        public GetImageFromURL(ImageView imgV) {
            this.imgV = imgV;
        }

        @Override
        protected Bitmap doInBackground(String... url) {
            String urldisplay = url[0];
            imgP = null;
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
