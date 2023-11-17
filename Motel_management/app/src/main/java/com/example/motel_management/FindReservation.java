package com.example.motel_management;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class FindReservation extends AppCompatActivity {
    Button button, dashboardBtn, btFindReservation;
    EditText etFirstName, etLastName, etConfirmation;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_reservation);

        progressDialog = new ProgressDialog(this);

        button = findViewById(R.id.logout);
        dashboardBtn = findViewById(R.id.dashboardBtn);
        btFindReservation = findViewById(R.id.btFindReservation);

        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etConfirmation = findViewById(R.id.etConfirmation);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(FindReservation.this, Login.class);
                startActivity(intent);
                finishAffinity();
            }
        });


        dashboardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FindReservation.this, MainActivity.class);
                startActivity(intent);
                finishAffinity();
            }
        });

        btFindReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findReservation();
            }
        });
    }

    private void findReservation() {
        if (TextUtils.isEmpty(String.valueOf(etFirstName.getText()))) {
            Toast.makeText(FindReservation.this, "Enter first name", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(String.valueOf(etLastName.getText()))) {
            Toast.makeText(FindReservation.this, "Enter last name", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(String.valueOf(etConfirmation.getText()))) {
            Toast.makeText(FindReservation.this, "Enter confirmation number", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.show();
        FirebaseFirestore.getInstance().collectionGroup("rooms")
                .whereEqualTo("reservationConfirmationNumber", etConfirmation.getText().toString())
                .whereEqualTo("reservationFirstName", etFirstName.getText().toString().toLowerCase())
                .whereEqualTo("reservationLastName", etLastName.getText().toString().toLowerCase())
                .limit(1)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        progressDialog.hide();
                        if (task.isSuccessful()) {

                            if (!task.getResult().isEmpty()) {
                                DocumentSnapshot res = task.getResult().getDocuments().get(0);
                                Intent intent = new Intent(FindReservation.this, ReservationDetailsActivity.class);
                                Bundle bundle = new Bundle();
                                Room room = new Room(
                                        res.getString("id"),
                                        res.getString("motelId"),
                                        res.getString("type"),
                                        res.getString("description"),
                                        res.getBoolean("occupied"),
                                        res.getString("imageUrl"),
                                        res.getLong("price"),
                                        res.getBoolean("checkInn")
                                );
                                bundle.putParcelable("room", room);
                                intent.putExtras(bundle);
                                startActivity(intent);
                                finish();

                            } else {
                                Toast.makeText(FindReservation.this, "No Results Found!", Toast.LENGTH_SHORT).show();
                            }


                        } else {
                            Toast.makeText(FindReservation.this, getString(R.string.went_wrong), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

}