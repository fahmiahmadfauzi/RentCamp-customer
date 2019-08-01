package com.if9.fahmi.User;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.if9.fahmi.R;
import com.if9.fahmi.Session.SessionManager;

public class HomeUserActivity extends AppCompatActivity {

    Button btnLogout;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_user);
        sessionManager = new SessionManager(this);


        btnLogout = findViewById(R.id.logout);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.logoutuser();
            }
        });
    }
}
