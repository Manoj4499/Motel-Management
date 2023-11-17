package com.example.motel_management;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class ForgotPasswordActivity extends AppCompatActivity {

    TextInputEditText emailtx1;
    Button confirmBtn;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        progressDialog = new ProgressDialog(this);

        emailtx1 = findViewById(R.id.email);
        confirmBtn = findViewById(R.id.btn_confirm);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(emailtx1.getText())) {
                    Toast.makeText(ForgotPasswordActivity.this, "Enter email", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressDialog.show();


    }
}