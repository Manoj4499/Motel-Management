package com.example.motel_management;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MotelsListActivity extends AppCompatActivity {

    ProgressDialog progressDialog;
    RecyclerView recyclerView;
    MotelsListAdapter adapter;

    Button btnDashboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motels);

        progressDialog = new ProgressDialog(this);

        recyclerView = findViewById(R.id.rv);
        btnDashboard = findViewById(R.id.btnDashboard);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getData();

        btnDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    void getData() {
        progressDialog.show();
        FirebaseFirestore.getInstance().collection("Motels").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                progressDialog.hide();
                if (task.isSuccessful()) {
                    List<Motel> motels = new ArrayList<>();
                    for (QueryDocumentSnapshot doc : task.getResult()) {
                        Motel motel = new Motel(
                                doc.getString("id"),
                                doc.getString("name"),
                                doc.getString("description"),
                                doc.getString("location"),
                                doc.getString("imageUrl"));
                        motels.add(motel);
                    }
                    adapter = new MotelsListAdapter(MotelsListActivity.this, motels);
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(MotelsListActivity.this, getString(R.string.went_wrong), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
