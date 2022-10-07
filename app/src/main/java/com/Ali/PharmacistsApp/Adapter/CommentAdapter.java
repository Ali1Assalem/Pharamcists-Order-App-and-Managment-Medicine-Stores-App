package com.Ali.PharmacistsApp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Ali.PharmacistsApp.Model.Comment;
import com.Ali.PharmacistsApp.R;
import com.Ali.PharmacistsApp.Utils.Common;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentViewHolder> {

    List<Comment> comments;
    Context context;

    public CommentAdapter(List<Comment> comments, Context context) {
        this.comments = comments;
        this.context = context;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CommentViewHolder(LayoutInflater.from(context).inflate(R.layout.comment_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Animation animation= AnimationUtils.loadAnimation(holder.itemView.getContext(), android.R.anim.slide_in_left);

        holder.txt_comment_name.setText(comments.get(position).getName());
        holder.txt_comment.setText(comments.get(position).getComment());
        holder.ratingBar.setRating(Float.parseFloat(comments.get(position).getValue()));

        String date=comments.get(position).getCreated_at();
        holder.txt_date.setText(date.substring(0,10));

        Picasso.get()
                .load(Common.BASE_URL_IMAGE +
                        comments.get(position).getImage())
                .into(holder.img_comment);

        holder.itemView.setAnimation(animation);

    }

    @Override
    public int getItemCount() {
        return comments.size();
    }
}
