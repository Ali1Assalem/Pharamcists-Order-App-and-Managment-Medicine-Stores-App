package com.Ali.PharmacistsApp.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Ali.PharmacistsApp.Interface.IItemClickListener;
import com.Ali.PharmacistsApp.Model.Menu;
import com.Ali.PharmacistsApp.ProductActivity;
import com.Ali.PharmacistsApp.R;
import com.Ali.PharmacistsApp.Utils.Common;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {

    Context context;
    List<Menu> menus;

    public MenuAdapter(Context context, List<Menu> menus) {
        this.context = context;
        this.menus = menus;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.menu_item_layout,parent,false);
        return new MenuViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder,@SuppressLint("RecyclerView") int position) {
        Animation animation= AnimationUtils.loadAnimation(holder.itemView.getContext(), android.R.anim.slide_in_left);

        Picasso.get()
                .load(menus.get(position).link)
                .error(R.drawable.img)
                .placeholder(R.drawable.img)
                .into(holder.img_Menu);


        holder.txt_Menu_name.setText(menus.get(position).name);

        holder.setItemClickListener(new IItemClickListener() {
            @Override
            public void onClick(View v) {
                Common.currentMenu=menus.get(position);

                context.startActivity(new Intent(context, ProductActivity.class));
            }
        });

        holder.itemView.setAnimation(animation);

    }

    @Override
    public int getItemCount() {
        return menus.size();
    }

    class MenuViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{

        IItemClickListener itemClickListener;

        public void setItemClickListener(IItemClickListener itemClickListener){
            this.itemClickListener=itemClickListener;
        }


        ImageView img_Menu;
        TextView txt_Menu_name;
        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);
            img_Menu = itemView.findViewById(R.id.menu_pic);
            txt_Menu_name = itemView.findViewById(R.id.menu_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view);
        }

    }
}
