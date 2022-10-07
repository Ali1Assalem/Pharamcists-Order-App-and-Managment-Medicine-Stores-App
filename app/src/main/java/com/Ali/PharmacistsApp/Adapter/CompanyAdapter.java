package com.Ali.PharmacistsApp.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Ali.PharmacistsApp.Interface.IItemClickListener;
import com.Ali.PharmacistsApp.MenuActivity;
import com.Ali.PharmacistsApp.Model.Company;
import com.Ali.PharmacistsApp.R;
import com.Ali.PharmacistsApp.Utils.Common;
import com.squareup.picasso.Picasso;


import java.util.List;

public class CompanyAdapter extends RecyclerView.Adapter<CompanyViewHolder> {

    Context context;
    List<Company> companies;

    public CompanyAdapter(Context context, List<Company> menus) {
        this.context = context;
        this.companies = menus;
    }

    @NonNull
    @Override
    public CompanyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.company_item_layout,null);
        return new CompanyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CompanyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Animation animation= AnimationUtils.loadAnimation(holder.itemView.getContext(), android.R.anim.slide_in_left);

        Picasso.get().load(companies.get(position).img)
                .into(holder.img_company);

        holder.name_company.setText(companies.get(position).name);
        holder.setItemClickListener(new IItemClickListener() {
            @Override
            public void onClick(View v) {
                Common.currentCompany=companies.get(position);

                context.startActivity(new Intent(context, MenuActivity.class));
            }
        });

        holder.itemView.setAnimation(animation);
    }

    @Override
    public int getItemCount() {
        return companies.size();
    }
}
