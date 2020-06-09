package com.example.myapplication;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class ViewHolder extends RecyclerView.ViewHolder {

    View mView ;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);

        mView = itemView;

    }

    public void setDetails (Context ctx, String title,String  description, String image){
        TextView mTitle = mView.findViewById(R.id.ArcTitle);
        TextView mDescription = mView.findViewById(R.id.ArcDesc);
        ImageView mImage = mView.findViewById(R.id.ArcimageView);

        mTitle.setText(title);
        mDescription.setText(description);
        Picasso.get().load(image).into(mImage);
    }
}
