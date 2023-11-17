package com.example.motel_management;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class ConfirmationPage extends AppCompatActivity {
    Button logout, dashboard;
    TextView tv;

    String details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation_page);
        logout = findViewById(R.id.logout);
        dashboard = findViewById(R.id.homeBTN);
        tv = findViewById(R.id.tvConfirm);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            details = bundle.getString("details");
        }

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(ConfirmationPage.this, Login.class);
                startActivity(intent);
                finishAffinity();
            }
        });

      
}
