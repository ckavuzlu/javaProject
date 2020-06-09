package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.protobuf.StringValue;
import com.squareup.picasso.Picasso;

public class ArticleDetails extends AppCompatActivity {

    ImageView art_image ;
    TextView art_title, art_description ;
    String art_tit , art_desc ,img_url;
    int art_img ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_page);

        art_image = findViewById(R.id.article_img);
        img_url = getIntent().getStringExtra("image");
        Picasso.get().load(img_url).into(art_image);

        art_description = findViewById(R.id.article_content);
        art_title = findViewById(R.id.article_title);


        art_tit = getIntent().getStringExtra("title");
        art_desc = getIntent().getStringExtra("content");

        
        art_description.setText(art_desc.replace("_b","\n"));
        art_title.setText(art_tit);

    }
}
