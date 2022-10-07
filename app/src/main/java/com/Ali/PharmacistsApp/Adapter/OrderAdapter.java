package com.Ali.PharmacistsApp.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Ali.PharmacistsApp.Database.ModelDB.Cart;
import com.Ali.PharmacistsApp.Interface.IItemClickListener;
import com.Ali.PharmacistsApp.Model.Order;
import com.Ali.PharmacistsApp.OrderActivity;
import com.Ali.PharmacistsApp.R;
import com.Ali.PharmacistsApp.Retrofit.Api;
import com.Ali.PharmacistsApp.Utils.Common;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderAdapter extends RecyclerView.Adapter<OrderViewHolder> {

    List<Order> orders;
    Context context;

    public OrderAdapter(List<Order> orders, Context context) {
        this.orders = orders;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(context).inflate(R.layout.order_layout,parent,false);
        return new OrderViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Animation animation= AnimationUtils.loadAnimation(holder.itemView.getContext(), android.R.anim.slide_in_left);

        holder.txt_order_id.setText(new StringBuilder("#").append(orders.get(position).getOrderId()));
        holder.txt_order_price.setText(new StringBuilder("$").append(orders.get(position).getOrderPrice()));
        holder.txt_order_address.setText(new StringBuilder("Address : ").append(orders.get(position).getOrderAddress()));
        holder.txt_company_name.setText(new StringBuilder("Comment : ").append(orders.get(position).getOrderComment()));
        holder.txt_order_status.setText(new StringBuilder("Order Status: ").append(Common.convertCodeToStatus(orders.get(position).getOrderStatus())));

        holder.delete_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        cancelOrder(String.valueOf(orders.get(position).getOrderId()),"-1",context);
                    }
            });

        Cart orderDetail=new Gson().fromJson(orders.get(position).getOrderDetail(),new TypeToken<Cart>(){}.getType());

        Picasso.get()
                .load(orderDetail.link)
                .into(holder.order_img);

        holder.txt_order_quantity.setText(new StringBuilder("Qty : ").append(orderDetail.amount));

        holder.txt_order_name.setText(new StringBuilder("Name : ").append(orderDetail.name));
        holder.setItemClickListener(new IItemClickListener(){
            @Override
            public void onClick(View v) {
                //Common.currentOrder=orders.get(position);
                //context.startActivity(new Intent(context, OrderDetailActivity.class));
            }
        });
        holder.itemView.setAnimation(animation);

    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    private void cancelOrder(String orderId,String orderStatus,Context context){
        Api mservice=Common.getApi();
        mservice.cancel_order(orderId,orderStatus).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Toast.makeText(context, response.body(), Toast.LENGTH_SHORT).show();
                if (response.body().contains("Cancelled Successfully")){
                    OrderActivity.loadOrder(Common.currentItem,context);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
