package com.example.motel_management;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ReservationDetailsActivity extends AppCompatActivity {

    Room room;
    TextView tvRoomType, tvDesc;
    ImageView iv;
    Button checkInBtn, checkOutBtn, dashboardBtn;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reseravtion_details);

        progressDialog = new ProgressDialog(this);
        tvDesc = findViewById(R.id.tvDesc);
        tvRoomType = findViewById(R.id.tvType);
        iv = findViewById(R.id.iv);
        checkInBtn = findViewById(R.id.btCheckIn);
        checkOutBtn = findViewById(R.id.btCheckOut);
        dashboardBtn = findViewById(R.id.btDashboard);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            room = bundle.getParcelable("room");
        }

        setUI();

        checkInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkIn();
            }
        });

        checkOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkOut();
            }
        });

        dashboardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReservationDetailsActivity.this, MainActivity.class);
                startActivity(intent);
                finishAffinity();
            }
        });

    }

    private void setUI() {
        if (room != null) {
            tvDesc.setText(room.getDescription());
            tvRoomType.setText(room.gettype());
            Glide.with(this).load(room.getImageUrl()).into(iv);
            if (room.getCheckIn()) {
                checkOutBtn.setVisibility(View.VISIBLE);
            } else {
                checkInBtn.setVisibility(View.VISIBLE);
            }
        }
    }

    private void checkIn() {
        progressDialog.show();
        Map<String, Object> updates = new HashMap<>();

        updates.put("checkInn", true);

        FirebaseFirestore.getInstance().collection("Motels")
                .document(room.getMotelId()).collection("rooms").document(room.getId())
                .update(updates).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressDialog.hide();
                        if (task.isSuccessful()) {
                            Toast.makeText(ReservationDetailsActivity.this, "Check In Success", Toast.LENGTH_SHORT).show();
                            checkOutBtn.setVisibility(View.VISIBLE);
                            checkInBtn.setVisibility(View.GONE);
                        } else {
                            Toast.makeText(ReservationDetailsActivity.this, "Unable to check in", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void checkOut() {
        progressDialog.show();
        Map<String, Object> updates = new HashMap<>();

        updates.put("checkInn", false);
        updates.put("occupied", false);
        updates.put("reservationConfirmationNumber", FieldValue.delete());
        updates.put("reservationFirstName", FieldValue.delete());
        updates.put("reservationLastName", FieldValue.delete());
        updates.put("reservation", FieldValue.delete());


        FirebaseFirestore.getInstance().collection("Motels")
                .document(room.getMotelId()).collection("rooms").document(room.getId())
                .update(updates).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ReservationDetailsActivity.this, "Check Out Success", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ReservationDetailsActivity.this, MainActivity.class);
                            startActivity(intent);
                            finishAffinity();
                        } else {
                            Toast.makeText(ReservationDetailsActivity.this, "Unable to check out", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}