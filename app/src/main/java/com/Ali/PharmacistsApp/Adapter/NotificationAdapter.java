package com.Ali.PharmacistsApp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Ali.PharmacistsApp.Model.Notifications;
import com.Ali.PharmacistsApp.Model.Order;
import com.Ali.PharmacistsApp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationViewHolder> {

    List<Notifications> notifications;
    Context context;

    public NotificationAdapter(List<Notifications> notifications, Context context) {
        this.notifications = notifications;
        this.context = context;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(context).inflate(R.layout.notification_item_layout,parent,false);
        return new NotificationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {

        Animation animation= AnimationUtils.loadAnimation(holder.itemView.getContext(), android.R.anim.slide_in_left);

        String date=notifications.get(position).getCreated_at();
        holder.txt_order_date.setText(date.substring(0,10));//2022-03-16 00-00-00

        holder.txt_order_price.setText(new StringBuilder("$").append(notifications.get(position).getOrderPrice()));
        holder.txt_order_data.setText(notifications.get(position).getData());
        holder.txt_order_quantity.setText(new StringBuilder("Qty : ").append(notifications.get(position).getOrderQty()));
        holder.txt_order_name.setText(notifications.get(position).getOrderName());
        Picasso.get()
                .load(notifications.get(position).getImg())
                .into(holder.order_img);

        holder.itemView.startAnimation(animation);
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

}
