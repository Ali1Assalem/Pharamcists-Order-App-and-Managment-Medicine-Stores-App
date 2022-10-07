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

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.Ali.PharmacistsApp.Database.ModelDB.Cart;
import com.Ali.PharmacistsApp.R;
import com.Ali.PharmacistsApp.Utils.Common;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CartAdapter extends  RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    List<Cart> carts;
    Context context;

    public CartAdapter(List<Cart> carts, Context context) {
        this.carts = carts;
        this.context = context;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(context).inflate(R.layout.cart_item_layout,parent,false);
        return new CartViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Picasso.get()
                .load(carts.get(position).link)
                .into(holder.img_product);

        holder.txt_amount.setNumber(String.valueOf(carts.get(position).amount));
        holder.txt_price.setText(new StringBuilder("$").append(carts.get(position).price));

        holder.txt_product_name.setText(new StringBuilder(carts.get(position).name)
                .append(" x")
                .append(carts.get(position).amount));

        holder.txt_company_name.setText("store name : "+carts.get(position).company);

        final double priceOneProduct=carts.get(position).price / carts.get(position).amount;

        //Auto save item when user change amount
        holder.txt_amount.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                Cart cart=carts.get(position);
                cart.amount=newValue;
                cart.price=priceOneProduct*newValue;

                Common.cartRepository.updateToCart(cart);

                holder.txt_price.setText(new StringBuilder("$").append(carts.get(position).price));
            }
        });

    }

    @Override
    public int getItemCount() {
        return carts.size();
    }


    public class CartViewHolder extends RecyclerView.ViewHolder{

        ImageView img_product;
        TextView txt_product_name, txt_price,txt_company_name;
        ElegantNumberButton txt_amount;

        public RelativeLayout view_background;
        public LinearLayout view_foreground;


        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            img_product = itemView.findViewById(R.id.img_product);
            txt_amount = itemView.findViewById(R.id.txt_amount);
            txt_product_name = itemView.findViewById(R.id.txt_product_name);
            txt_product_name = itemView.findViewById(R.id.txt_product_name);
            txt_company_name= itemView.findViewById(R.id.txt_company);
            txt_price = itemView.findViewById(R.id.txt_price);

            view_background=itemView.findViewById(R.id.view_background);
            view_foreground=itemView.findViewById(R.id.view_foreground);
        }
    }

    public void removeItem(int position)
    {
        carts.remove(position);
        notifyItemRemoved(position);
    }
    public void restoreItem(Cart item, int position)
    {
        carts.add(position,item);
        notifyItemInserted(position);
    }
}
