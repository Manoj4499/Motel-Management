package com.example.motel_management;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class Billing extends AppCompatActivity {
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing);
        Button loginButton = findViewById(R.id.ConfirmBT);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Billing.this, ConfirmationPage.class);

                startActivity(intent);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent= new Intent(Billing.this,Login.class);
                startActivity(intent);
                finish();
            }
        });
        Button dashboardbtn = findViewById(R.id.dashboardBtn);
        dashboardbtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Billing.this, SecondActivity.class);

                startActivity(intent);
            }
        });
    }}