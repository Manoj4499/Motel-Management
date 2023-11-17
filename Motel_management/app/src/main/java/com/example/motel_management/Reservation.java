package com.example.motel_management;

import android.os.Bundle;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;

import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Reservation extends AppCompatActivity {

    Room room;

    EditText firstNameET, lastNameET, addressET, phNumberET, idProofET;
    TextView date;
    Date startingDate, endingDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            room = bundle.getParcelable("room");
        }

        firstNameET = findViewById(R.id.firstNameET);
        lastNameET = findViewById(R.id.lastNameET);
        addressET = findViewById(R.id.addressET);
        phNumberET = findViewById(R.id.phNumberET);
        idProofET = findViewById(R.id.idProofET);
        date = findViewById(R.id.tvDate);

        Button reserveButton = findViewById(R.id.reserveBTN);


        reserveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(String.valueOf(firstNameET.getText()))) {
                    Toast.makeText(Reservation.this, "Enter first name", Toast.LENGTH_SHORT).show();
                    return;
                }
                



                ReservationModel rm =
                        new ReservationModel(room,
                                String.valueOf(firstNameET.getText()),
                                String.valueOf(lastNameET.getText()),
                                String.valueOf(addressET.getText()),
                                String.valueOf(phNumberET.getText()),
                                String.valueOf(idProofET.getText()),
                                startingDate,
                                endingDate
                        );

                Intent intent = new Intent(Reservation.this, Billing.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("reservation", rm);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });


        Button dashboardbtn = findViewById(R.id.dashboardBtn);
        dashboardbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Reservation.this, MainActivity.class);
                startActivity(intent);
                finishAffinity();
            }
        });

        Button button = findViewById(R.id.logout);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(Reservation.this, Login.class);
                startActivity(intent);
                finishAffinity();

            }
        });

        date.setOnClickListener(view -> {

            CalendarConstraints.Builder constraintsBuilder = new CalendarConstraints.Builder();
            constraintsBuilder.setValidator(DateValidatorPointForward.now());

            MaterialDatePicker<Pair<Long, Long>> dateRangePicker = MaterialDatePicker.Builder.dateRangePicker()
                    .setTitleText("Select Date Range")
                    .setCalendarConstraints(constraintsBuilder.build())
                    .build();

            dateRangePicker.addOnPositiveButtonClickListener(selection -> {
                updateDateRange(selection.first, selection.second);
            });

            dateRangePicker.show(getSupportFragmentManager(), dateRangePicker.toString());
        });
    }

    private void updateDateRange(long startDate, long endDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        String formattedStartDate = sdf.format(new Date(startDate));
        String formattedEndDate = sdf.format(new Date(endDate));

        startingDate = new Date(startDate);
        endingDate = new Date(endDate);

        String dateRangeText = formattedStartDate + " - " + formattedEndDate;
        date.setText(dateRangeText);
    }
}