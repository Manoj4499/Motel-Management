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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MotelDetailsActivity extends AppCompatActivity {

    Motel motel;
    ProgressDialog progressDialog;
    RecyclerView recyclerView;
    RoomsListAdapter adapter;
    Button btnDashboard;
    TextView tvMotelName, tvDesc, tvLocation;

    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motel_details);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            motel = bundle.getParcelable("motel");
        }
        progressDialog = new ProgressDialog(this);

        recyclerView = findViewById(R.id.rvRooms);
        btnDashboard = findViewById(R.id.btnDashboard);
        tvMotelName = findViewById(R.id.tvName);
        tvLocation = findViewById(R.id.tvLocation);
        tvDesc = findViewById(R.id.tvDesc);
        iv = findViewById(R.id.iv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        setData();
        getData();

        btnDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MotelDetailsActivity.this, MainActivity.class);
                startActivity(intent);
                finishAffinity();
            }
        });

    }

    void setData() {
        if (motel != null) {
            tvMotelName.setText(motel.getName());
            tvLocation.setText(motel.getLocation());
            tvDesc.setText(motel.getDescription());
            Glide.with(this).load(motel.getImageUrl()).into(iv);

        }
    }

    void getData() {
        progressDialog.show();
        FirebaseFirestore.getInstance().collection("Motels").document(motel.getId())
                .collection("rooms").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        progressDialog.hide();
                        if (task.isSuccessful()) {
                            List<Room> rooms = new ArrayList<>();
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                Room room = new Room(
                                        doc.getString("id"),
                                        doc.getString("motelId"),
                                        doc.getString("type"),
                                        doc.getString("description"),
                                        doc.getBoolean("occupied"),
                                        doc.getString("imageUrl"),
                                        doc.getLong("price"),
                                        doc.getBoolean("checkIn")
                                );
                                rooms.add(room);
                            }
                            adapter = new RoomsListAdapter(MotelDetailsActivity.this, rooms);
                            recyclerView.setAdapter(adapter);
                        } else {
                            Toast.makeText(MotelDetailsActivity.this, getString(R.string.went_wrong), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
