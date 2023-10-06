package com.example.motel_management;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        ImageButton makeReservationButton = findViewById(R.id.imageButtonMakeReservation);
        ImageButton findReservationButton = findViewById(R.id.imageButtonFindReservation);
        makeReservationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondActivity.this,Reservation.class);
                startActivity(intent);
            }
        });
        findReservationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondActivity.this, FindReservation.class);
                startActivity(intent);
            }
        });
    }
}
