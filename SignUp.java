package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SignUp extends AppCompatActivity {
    ProgressBar progressBar;
    FirebaseAuth mAuth ;
    Button goSignIn , btnSignUp;
    MaterialEditText edtNickname , edtEmail ,  edtPassword , edtPassword2 ;
    String userName , userEmail  , userPassword , userPassword2 ;
    FirebaseUser currentUser;
    FirebaseDatabase database ;
    DatabaseReference myRef ;

    FirebaseAuth auth ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        goSignIn = findViewById(R.id.btnGoSignIn);
        btnSignUp = findViewById(R.id.btnSignUp);
        progressBar = findViewById(R.id.SignUpProgressBar);
        edtNickname = findViewById(R.id.SignUpName);
        edtEmail = findViewById(R.id.SignUpEMail);
        edtPassword = findViewById(R.id.SignUpPassword);
        edtPassword2 = findViewById(R.id.SignUpPassword2);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();


        final UserInfo user = new UserInfo();

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                userName = edtNickname.getText().toString();
                userEmail = edtEmail.getText().toString();
                userPassword = edtPassword.getText().toString();
                userPassword2 = edtPassword2.getText().toString();

                if (TextUtils.isEmpty(userName) | TextUtils.isEmpty(userEmail)
                        | TextUtils.isEmpty(userPassword) | TextUtils.isEmpty(userPassword2)) {
                    Toast.makeText(SignUp.this, "Lütfen Tüm bilgileri Eksiksiz Doldurunuz.", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);

                    return;
                }
                if (!TextUtils.equals(userPassword, userPassword2)) {
                    Toast.makeText(SignUp.this, "Şifreler Eşleşmemektedir.", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);

                    return;
                }

                if(userPassword.length() < 7){
                    Toast.makeText(SignUp.this, "Şifreniz 6 haneden uzun olmalıdır.", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    return;
                }



                mAuth.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()) {

                        String userUid = mAuth.getCurrentUser().getUid();

                        myRef = database.getReference().child("Users").child(userUid);
                        myRef.child("name").setValue(userName);
                        myRef.child("email").setValue(userEmail);
                        myRef.child("uid").setValue(userUid);

                        Intent goSignIn = new Intent(SignUp.this, SignIn.class);
                        Toast.makeText(SignUp.this, "Üyeliğiniz Oluşturuldu, giriş yapabilirsiniz.", Toast.LENGTH_SHORT).show();
                        startActivity(goSignIn);
                        progressBar.setVisibility(View.GONE);

                        finish();
                    }
                    else {
                        Toast.makeText(SignUp.this, "Bilinmeyen bir hata oluştu, bilgilerinizi kontrol edip tekrar deneyiniz.", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        return;
                    }

                    }
                });

            }
        });


        goSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goSignIn = new Intent(SignUp.this , SignIn.class);
                startActivity(goSignIn);
            }
        });

    }
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Üyelik oluşturmadın, çıkmak istiyor musun?")
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
