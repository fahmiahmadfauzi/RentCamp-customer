package com.if9.fahmi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class FavoriteActivity extends AppCompatActivity {

    Toolbar toolbarFav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        toolbarFav = findViewById(R.id.toolbarfav);
        setSupportActionBar(toolbarFav);
        getSupportActionBar().setTitle("Favorite");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
