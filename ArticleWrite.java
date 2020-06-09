package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.net.URI;
import java.util.UUID;

public class ArticleWrite extends AppCompatActivity {
    BottomNavigationView nav_View;
    ImageView add_article_Image ;
    Button share_article ;
    private Uri filePath ;
    private final int  PICK_IMAGE_REQUEST = 71 ;
    FirebaseStorage storage ;
    StorageReference storageReference ;
    FirebaseDatabase mDatabase ;
    DatabaseReference myRef ;
    String ImageUid , ArticleUid ;
    EditText add_content, add_title ,add_description ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_write);
        ArticleUid = UUID.randomUUID().toString();
        mDatabase = FirebaseDatabase.getInstance();

        add_content = findViewById(R.id.add_article_content);
        add_title = findViewById(R.id.add_article_title);
        add_description = findViewById(R.id.add_article_description);

        myRef = mDatabase.getReference().child("Data").child(ArticleUid);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        nav_View = findViewById(R.id.nav_view_article_writing);
        nav_View.setOnNavigationItemSelectedListener(navListener);

        nav_View.setSelectedItemId(R.id.favorites);

        share_article = findViewById(R.id.Share_Article);

        add_article_Image =  findViewById(R.id.add_article_image);
        add_article_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();

            }
        });

        share_article.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(ArticleWrite.this)
                        .setTitle("Makaleyi yayınlamak istiyor musun?")
                        .setPositiveButton("Evet", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                uploadImage();
                                myRef.child("Content").setValue(add_content.getText().toString());
                                myRef.child("Title").setValue(add_title.getText().toString());
                                myRef.child("Description").setValue(add_description.getText().toString());
                                myRef.child("Image").setValue("https://firebasestorage.googleapis.com/v0/b/myapp323-82500.appspot.com/o/indian-5137640_1920.jpg?alt=media&token=eb507c42-decf-4892-9ba4-1e55b23587c2").toString();
                            }

                        })
                        .setNegativeButton("Hayır", null)
                        .show();
            }

        });

    }

    private void uploadImage() {

    }


    private void chooseImage() {
        Intent i = new Intent();
        i.setType("image/+");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
         && data != null &&  data.getData() != null){
            filePath = data.getData();
            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                add_article_Image.setImageBitmap(bitmap);
                add_article_Image.setScaleType(ImageView.ScaleType.CENTER_CROP);
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch (item.getItemId()){

                        case R.id.home:
                            Intent in = new Intent(ArticleWrite.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(in);
                            break ;
                        case R.id.favorites:
                            break;
                        case R.id.settings:
                            Intent i = new Intent(ArticleWrite.this, Settings.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(i);
                            break;

                    }
                    return true;
                }
            };
}