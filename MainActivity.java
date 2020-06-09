package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.load.model.Model;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    RecyclerView mRecyclerView;
    FirebaseFirestore firebaseFirestore;
    Adapter adapter;
    ArrayList<Article> list ;
    LinearLayoutManager manager;
    SwipeRefreshLayout swipeRefreshLayout;
    FirebaseAuth mAuth ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        bottomNavigationView = findViewById(R.id.nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        bottomNavigationView.setSelectedItemId(R.id.home);
        firebaseFirestore = FirebaseFirestore.getInstance();
        mRecyclerView =  findViewById(R.id.recyclerview);
        manager = new LinearLayoutManager(this);
        list = new ArrayList<>();
        mRecyclerView.setLayoutManager(manager);
        swipeRefreshLayout = findViewById(R.id.refresh);







        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                refreshData();
            }

        });
        FetchData();


    }

    private void refreshData(){
        FetchData();
        swipeRefreshLayout.setRefreshing(false);

    }
    private void FetchData() {
        list.clear();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("Data")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                            String Title = snapshot.child("Title").getValue().toString();
                            String Description = snapshot.child("Description").getValue().toString();
                            String Image = snapshot.child("Image").getValue().toString();
                            String Content = snapshot.child("Content").getValue().toString();

                            list.add(new Article(Title, Description, Image, Content));
                        }

                        adapter = new Adapter(MainActivity.this, list);
                        mRecyclerView.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(MainActivity.this, databaseError.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Üyelikten çıkıyorsunuz, çıkmak istiyor musun?")
                .setPositiveButton("Evet", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mAuth.signOut();
                        Intent i = new Intent(MainActivity.this,FirstScreen.class);
                        finish();
                    }

                })
                .setNegativeButton("Hayır", null)
                .show();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch (item.getItemId()){

                        case R.id.home:

                            break;
                        case R.id.favorites:
                            Intent i = new Intent(MainActivity.this, ArticleWrite.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(i);
                            break;
                        case R.id.settings:
                            Intent in = new Intent(MainActivity.this, Settings.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(in);
                            break;

                    }
                    return true;
                }
            };

    }




