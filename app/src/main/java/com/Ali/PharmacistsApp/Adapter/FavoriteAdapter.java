package com.Ali.PharmacistsApp.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Ali.PharmacistsApp.Database.ModelDB.Favorite;
import com.Ali.PharmacistsApp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {
    Context context;
    List<Favorite> favoriteList;

    public FavoriteAdapter(Context context, List<Favorite> favoriteList) {
        this.context = context;
        this.favoriteList = favoriteList;
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(context).inflate(R.layout.fav_item_layout,parent,false);
        return new FavoriteViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
        Animation animation= AnimationUtils.loadAnimation(holder.itemView.getContext(), android.R.anim.slide_in_left);

        Picasso.get()
                .load(favoriteList.get(position).link)
                .into(holder.img_product);

        holder.txt_price.setText(new StringBuilder("$").append(favoriteList.get(position).price).toString());
        holder.txt_product_name.setText(favoriteList.get(position).name);
        holder.txt_company.setText("Company : "+favoriteList.get(position).company);

        holder.itemView.setAnimation(animation);
}

    @Override
    public int getItemCount() {
        return favoriteList.size();
    }


    public class FavoriteViewHolder extends RecyclerView.ViewHolder
    {
        ImageView img_product;
        TextView txt_product_name,txt_price,txt_company;

        public RelativeLayout view_background;
        public LinearLayout view_foreground;

        public FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            img_product=itemView.findViewById(R.id.img_product);
            txt_product_name=itemView.findViewById(R.id.txt_product_name);
            txt_company=itemView.findViewById(R.id.txt_company);
            txt_price=itemView.findViewById(R.id.txt_price);

            view_background=itemView.findViewById(R.id.view_background);
            view_foreground=itemView.findViewById(R.id.view_foreground);
        }
    }
    public void removeItem(int position)
    {
        favoriteList.remove(position);
        notifyItemRemoved(position);
    }
    public void restoreItem(Favorite item,int position)
    {
        favoriteList.add(position,item);
        notifyItemInserted(position);
    }
}
