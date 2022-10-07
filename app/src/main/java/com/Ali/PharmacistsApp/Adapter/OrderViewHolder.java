package com.Ali.PharmacistsApp.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Ali.PharmacistsApp.Interface.IItemClickListener;
import com.Ali.PharmacistsApp.R;

public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView txt_order_id,txt_order_price,txt_order_address,txt_company_name,
            txt_order_status,txt_order_quantity,txt_order_name;
    public ImageView order_img,delete_img;
    IItemClickListener itemClickListener;

    public void setItemClickListener(IItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public OrderViewHolder(@NonNull View itemView) {
        super(itemView);

        txt_order_address=itemView.findViewById(R.id.txt_order_address);
        txt_order_id=itemView.findViewById(R.id.txt_order_id);
        txt_order_price=itemView.findViewById(R.id.txt_order_price);
        txt_company_name=itemView.findViewById(R.id.txt_company_name);
        txt_order_status=itemView.findViewById(R.id.txt_order_status);
        txt_order_quantity=itemView.findViewById(R.id.txt_order_quantity);
        txt_order_name=itemView.findViewById(R.id.txt_order_name);
        order_img=itemView.findViewById(R.id.order_img);
        delete_img=itemView.findViewById(R.id.cancelOrder);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v);
    }
}
