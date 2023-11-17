package com.example.motel_management;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class Billing extends AppCompatActivity {

    ReservationModel reservationModel;
    Button confirmButton, logOut;
    private Spinner spinner;
    private LinearLayout cardLayout;
    private EditText etCardNumber, etExpiration, etNameOnCard, etZipCode, etAmount;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            reservationModel = bundle.getParcelable("reservation");
        }

        progressDialog = new ProgressDialog(this);

        confirmButton = findViewById(R.id.btConfirm);
        logOut = findViewById(R.id.logout);
        cardLayout = findViewById(R.id.llCardDetails);
        spinner = findViewById(R.id.spinner);

        etCardNumber = findViewById(R.id.etCardNumber);
        etExpiration = findViewById(R.id.etExpiration);
        etNameOnCard = findViewById(R.id.etNameOnCard);
        etZipCode = findViewById(R.id.etZipCode);
        etAmount = findViewById(R.id.etAmount);

        etAmount.setText("$ " + reservationModel.getRoom().getPrice());

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(Billing.this, Login.class);
                startActivity(intent);
                finish();
            }
        });

        setSpinner();

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirm();
            }
        });

    }

    private void setSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.payment_methods,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                if (pos == 0) {
                    cardLayout.setVisibility(View.VISIBLE);
                }
                if (pos == 1) {
                    cardLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void confirm() {
        //card
        if (spinner.getSelectedItemPosition() == 0) {
            if (TextUtils.isEmpty(String.valueOf(etCardNumber.getText()))) {
                Toast.makeText(Billing.this, "Please enter card number", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(String.valueOf(etExpiration.getText()))) {
                Toast.makeText(Billing.this, "Please enter expiry date", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(String.valueOf(etNameOnCard.getText()))) {
                Toast.makeText(Billing.this, "Please enter name", Toast.LENGTH_SHORT).show();
                return;
            }
        } else {
            if (TextUtils.isEmpty(String.valueOf(etZipCode.getText()))) {
                Toast.makeText(Billing.this, "Please enter zipcode", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        confirmReservation();

    }

    void confirmReservation() {

        progressDialog.show();
        Room room = reservationModel.getRoom();

        Map<String, Object> updates = new HashMap<>();
        Map<String, Object> reservationDetails = new HashMap<>();

        String confirmationNumber = String.valueOf(generateDigitRandom());
        updates.put("occupied", true);
        updates.put("checkInn", false);
        updates.put("reservationConfirmationNumber", confirmationNumber);
        updates.put("reservationFirstName", reservationModel.getFirstName().toLowerCase());
        updates.put("reservationLastName", reservationModel.getLastName().toLowerCase());


        String days = String.valueOf(getDaysDifference(reservationModel.getStartDate(), reservationModel.getEndDate()));
        reservationDetails.put("email", FirebaseAuth.getInstance().getCurrentUser().getEmail());
        reservationDetails.put("idProof", reservationModel.getIdProof());
        reservationDetails.put("address", reservationModel.getAddress());
        reservationDetails.put("phoneNumber", reservationModel.getPhoneNumber());
        reservationDetails.put("zipCode", etZipCode.getText().toString());
        reservationDetails.put("checkInDate", reservationModel.getStartDate());
        reservationDetails.put("checkOutDate", reservationModel.getEndDate());
        reservationDetails.put("totalDays", days);

        updates.put("reservation", reservationDetails);

        FirebaseFirestore.getInstance().collection("Motels")
                .document(room.getMotelId()).collection("rooms")
                .document(room.getId()).update(updates).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressDialog.hide();
                        if (task.isSuccessful()) {
                            Toast.makeText(Billing.this, "Reservation Done!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Billing.this, ConfirmationPage.class);
                            Bundle bundle = new Bundle();

                            String details = "Your Reservation is Confirmed\nFirst Name: " + reservationModel.getFirstName() + "\nLast Name: " +
                                    reservationModel.getLastName() + "\n Confirmation Number: " + confirmationNumber + "\n" + getDateFormatted(reservationModel.getStartDate(), reservationModel.getEndDate()) +
                                    "\nTotal Days: " + days;

                            bundle.putString("details", details);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            finishAffinity();

                        } else {
                            Toast.makeText(Billing.this, "Unable to reserve. Please try again!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private long generateDigitRandom() {
        Random random = new Random();
        return 1000000 + random.nextInt(9000000);
    }

    public static long getDaysDifference(Date startDate, Date endDate) {
        long differenceMillis = endDate.getTime() - startDate.getTime();
        return differenceMillis / (24 * 60 * 60 * 1000);
    }

    private String getDateFormatted(Date startDate, Date endDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        String formattedStartDate = sdf.format(startDate);
        String formattedEndDate = sdf.format(endDate);
        return formattedStartDate + " - " + formattedEndDate;
    }

}