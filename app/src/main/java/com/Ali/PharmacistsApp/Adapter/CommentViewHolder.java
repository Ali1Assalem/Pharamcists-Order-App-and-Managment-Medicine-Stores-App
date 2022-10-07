package com.Ali.PharmacistsApp.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Ali.PharmacistsApp.R;

public class CommentViewHolder extends RecyclerView.ViewHolder {

    TextView txt_comment_name,txt_comment,txt_date;
    RatingBar ratingBar;
    ImageView img_comment;

    public CommentViewHolder(@NonNull View itemView) {
        super(itemView);
        txt_comment_name=itemView.findViewById(R.id.txt_comment_name);
        txt_comment=itemView.findViewById(R.id.txt_comment);
        ratingBar=itemView.findViewById(R.id.rating_bar);
        img_comment=itemView.findViewById(R.id.img_comment);
        txt_date=itemView.findViewById(R.id.txt_comment_date);
    }
}
