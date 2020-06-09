package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ArticleViewHolder>{

    private Context ctx;
    private ArrayList<Article> list;

    public Adapter(Context ctx, ArrayList<Article> list) {
        this.ctx = ctx;
        this.list = list;
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(ctx).inflate(R.layout.row,parent,false);
        return new ArticleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        final Article article =  list.get(position);

        holder.title.setText(article.getTitle());
        holder.description.setText(article.getDescription());
        holder.content.setText(article.getContent());
        Picasso.get().load(article.getImage()).into(holder.image);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ctx, ArticleDetails.class);
                intent.putExtra("image", article.getImage());
                intent.putExtra("title", article.getTitle());
                intent.putExtra("content", article.getContent());
                ctx.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ArticleViewHolder extends RecyclerView.ViewHolder{

        TextView  title,description, content;
        ImageView image;
        CardView cardView;
        ScrollView scrollView ;

        public ArticleViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.ArcTitle);
            description = itemView.findViewById(R.id.ArcDesc);
            image = itemView.findViewById(R.id.ArcimageView);
            cardView = itemView.findViewById(R.id.cardView);
            content = itemView.findViewById(R.id.ArcContent);

        }
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
