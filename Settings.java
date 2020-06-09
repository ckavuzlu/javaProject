package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.ServiceWorkerClient;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class Settings extends AppCompatActivity {

    Button signOut ;
    FirebaseAuth mAuth ;
    BottomNavigationView nav_View;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        signOut = findViewById(R.id.SignOut);
        mAuth = FirebaseAuth.getInstance();
        nav_View = findViewById(R.id.nav_view_settings);
        nav_View.setOnNavigationItemSelectedListener(navListener);

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent in = new Intent(Settings.this, FirstScreen.class);
                startActivity(in);
            }
        });


        nav_View.setSelectedItemId(R.id.settings);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch (item.getItemId()){

                        case R.id.home:
                            Intent in = new Intent(Settings.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(in);
                            break ;
                        case R.id.favorites:
                            Intent i = new Intent(Settings.this, ArticleWrite.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(i);
                            break;
                        case R.id.settings:
                            break;

                    }
                    return true;
                }
            };
}