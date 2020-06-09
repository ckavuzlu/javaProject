package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirstScreen extends AppCompatActivity {

    Button btnSignIn ,  btnSignUp ;
    FirebaseAuth mAuth ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_screen);

        btnSignIn = findViewById(R.id.SignIn);
        btnSignUp = findViewById(R.id.SignUp);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser != null) {
            Intent goLogin = new Intent(FirstScreen.this, MainActivity.class);
            startActivity(goLogin);
        }
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goSignInPage = new Intent(FirstScreen.this , SignIn.class);
                startActivity(goSignInPage);
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goSignUpPage = new Intent(FirstScreen.this , SignUp.class);
                startActivity(goSignUpPage);
            }
        });



    }


    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Uygulamadan çıkmak istediğine emin misin?")
                .setPositiveButton("Evet", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setNegativeButton("Hayır", null)
                .show();
    }

}

