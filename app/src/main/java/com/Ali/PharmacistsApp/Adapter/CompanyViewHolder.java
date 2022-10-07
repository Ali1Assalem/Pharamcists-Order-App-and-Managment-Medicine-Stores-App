package com.Ali.PharmacistsApp.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Ali.PharmacistsApp.Interface.IItemClickListener;
import com.Ali.PharmacistsApp.R;

public class CompanyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    ImageView img_company;
    TextView name_company;

    IItemClickListener itemClickListener;

    public void setItemClickListener(IItemClickListener itemClickListener){
        this.itemClickListener=itemClickListener;
    }
    public CompanyViewHolder(@NonNull View itemView) {
        super(itemView);

        img_company=itemView.findViewById(R.id.company_pic);
        name_company=itemView.findViewById(R.id.company_name);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view);
    }
}
