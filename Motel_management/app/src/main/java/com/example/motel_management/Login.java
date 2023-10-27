package com.example.motel_management;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    TextInputEditText emailtx1,passwordtx1;
    Button loginbtn;
    FirebaseAuth mAuth;
    ProgressBar progressbar;
    TextView regnow;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent= new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth=FirebaseAuth.getInstance();
        emailtx1=findViewById(R.id.email);
        passwordtx1=findViewById(R.id.password);
        loginbtn=findViewById(R.id.btn_login);
        //progressbar = findViewById(R.id.progressbar);
        regnow=findViewById(R.id.registernow);
        regnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getApplicationContext(),Registration.class);
                startActivity(intent);
                finish();
            }


        });

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //progressbar.setVisibility(View.VISIBLE);
                String email,password;
                email=String.valueOf(emailtx1.getText());
                password=String.valueOf(passwordtx1.getText());

                if(TextUtils.isEmpty(email)){

                    Toast.makeText(Login.this,"Enter email",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){

                    Toast.makeText(Login.this,"Enter password",Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                //progressbar.setVisibility(view.VISIBLE);
                                if (task.isSuccessful()) {
                                    Toast.makeText(Login.this, "Authentication Success.",
                                            Toast.LENGTH_SHORT).show();
                                    Intent intent= new Intent(getApplicationContext(),MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {


                                    Toast.makeText(Login.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }
        });
    }
}