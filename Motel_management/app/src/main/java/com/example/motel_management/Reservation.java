package com.example.motel_management;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class Reservation extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        Button reserveButton = findViewById(R.id.reserveBTN);

        reserveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Reservation.this, Billing.class);
                startActivity(intent);
            }
        });


        Button dashboardbtn = findViewById(R.id.dashboardBtn);
        dashboardbtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Reservation.this, SecondActivity.class);

                startActivity(intent);
            }
        });

        Button button = findViewById(R.id.logout);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(Reservation.this, Login.class);
                startActivity(intent);
                finish();

            }
        });
    }
}