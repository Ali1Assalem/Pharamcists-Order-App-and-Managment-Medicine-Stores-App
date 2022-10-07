package com.Ali.PharmacistsApp.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Ali.PharmacistsApp.R;

public class NotificationViewHolder extends RecyclerView.ViewHolder {

    public TextView txt_order_date,txt_order_price,txt_order_data,
            txt_order_quantity,txt_order_name;
    public ImageView order_img;

    public NotificationViewHolder(@NonNull View itemView) {
        super(itemView);
        txt_order_date=itemView.findViewById(R.id.txt_order_date);
        txt_order_price=itemView.findViewById(R.id.txt_order_price);
        txt_order_data=itemView.findViewById(R.id.txt_order_data);
        txt_order_quantity=itemView.findViewById(R.id.txt_order_quantity);
        txt_order_name=itemView.findViewById(R.id.txt_order_name);
        order_img=itemView.findViewById(R.id.order_img);
    }
}
