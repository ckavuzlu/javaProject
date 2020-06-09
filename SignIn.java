package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SignIn extends AppCompatActivity {

    MaterialEditText edtEmail , edtPassword ;
    Button goSignUp , btnSignIn;
    FirebaseAuth auth ;
    String userEmail , userPassword ;
    ProgressBar progressBar ;
    FirebaseAuth mAuth ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        goSignUp = findViewById(R.id.btnGoSignUp);
        btnSignIn =  findViewById(R.id.btnSignIn);
        auth = FirebaseAuth.getInstance();
        edtEmail = findViewById(R.id.edtEMail);
        edtPassword = findViewById(R.id.edtPassword);
        progressBar = findViewById(R.id.SignInProgressBar);
        mAuth = FirebaseAuth.getInstance();



        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                userEmail = edtEmail.getText().toString();
                userPassword = edtPassword.getText().toString();

                mAuth.signInWithEmailAndPassword(userEmail,userPassword).addOnCompleteListener(SignIn.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent goLogin = new Intent(SignIn.this, MainActivity.class);
                            startActivity(goLogin);
                            //updateUI(user);
                            progressBar.setVisibility(View.GONE);
                        }
                        else{
                            Toast.makeText(SignIn.this, "Hata oluştu, bilgilerinizi kontrol edip tekrar deneyiniz.", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                            return;
                        }
                    }
                });
            }
        });




        goSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goSignUp = new Intent(SignIn.this , SignUp.class);
                startActivity(goSignUp);
            }
        });

    }

}
