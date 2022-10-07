package com.Ali.PharmacistsApp.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Ali.PharmacistsApp.Database.ModelDB.Cart;
import com.Ali.PharmacistsApp.Database.ModelDB.Favorite;
import com.Ali.PharmacistsApp.Model.Product;
import com.Ali.PharmacistsApp.Model.ProductWithCompany;
import com.Ali.PharmacistsApp.Navigation_BasedActivity;
import com.Ali.PharmacistsApp.ProductDetailActivity;
import com.Ali.PharmacistsApp.R;
import com.Ali.PharmacistsApp.Utils.Common;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductViewHolder> {

    List<Product> products;
    Context context;

    public ProductAdapter(List<Product> products, Context context) {
        this.products = products;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(context).inflate(R.layout.product_item_layout,null);
        return new ProductViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Animation animation= AnimationUtils.loadAnimation(holder.itemView.getContext(), android.R.anim.slide_in_left);

        holder.txt_price.setText(new StringBuilder("$").append(products.get(position).price).toString());
        holder.txt_drink_name.setText(products.get(position).name);

        Picasso.get()
                .load(products.get(position).img)
                .into(holder.imgProduct);

        holder.btn_add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddToCartDialog(position);
            }
        });


        holder.imgProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Common.currentProduct=products.get(position);
                context.startActivity(new Intent(context, ProductDetailActivity.class));
            }
        });
        holder.txt_drink_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddToCartDialog(position);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
            }
        });

        //favorite system
        if (Common.favoriteRepository.isFavorite(Integer.parseInt(products.get(position).id))==1)
            holder.btn_favorite.setImageResource(R.drawable.favorite_red);
        else
            holder.btn_favorite.setImageResource(R.drawable.favorite);

        holder.btn_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Common.favoriteRepository.isFavorite(Integer.parseInt(products.get(position).id))!=1)
                {
                    addOrRemoveToFavorite(products.get(position),true);
                    holder.btn_favorite.setImageResource(R.drawable.favorite_red);
                }
                else
                {
                    addOrRemoveToFavorite(products.get(position),false);
                    holder.btn_favorite.setImageResource(R.drawable.favorite);
                }

            }
        });

        holder.itemView.setAnimation(animation);

    }





    private void addOrRemoveToFavorite(Product product, boolean isAdd) {
        Favorite favorite=new Favorite();
        favorite.id=product.id;
        favorite.link=product.img;
        favorite.name=product.name;
        favorite.price=product.price;
        favorite.company=product.company_name;
        favorite.menuId=product.menu_id;

        if (isAdd)
            Common.favoriteRepository.insertFav(favorite);
        else
            Common.favoriteRepository.delete(favorite);
    }


    @SuppressLint("SetTextI18n")
    private void showAddToCartDialog(int position) {
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        View itemView=LayoutInflater.from(context).inflate(R.layout.add_to_cart_layout,null);

        ImageView img_product_dialog=itemView.findViewById(R.id.img_cart_product);
        ElegantNumberButton txt_count=itemView.findViewById(R.id.txt_count);
        TextView txt_product_dialog=itemView.findViewById(R.id.txt_cart_product_name);
        TextView txt_company_dialog=itemView.findViewById(R.id.txt_cart_company_name);

        EditText edt_comment=itemView.findViewById(R.id.edt_comment);


        Picasso.get()
                .load(products.get(position).img)
                .into(img_product_dialog);

        txt_product_dialog.setText(products.get(position).name);
        txt_company_dialog.setText("company : "+products.get(position).company_name);


        builder.setView(itemView);
        builder.setNegativeButton("ADD TO CART", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showConfirmDialog(position,txt_count.getNumber(),products.get(position).company_name);
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void showConfirmDialog(int position, String number,String company_name) {
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        View itemView=LayoutInflater.from(context).inflate(R.layout.confirm_add_to_cart_layout,null);


        ImageView img_product_dialog=itemView.findViewById(R.id.img_product);
        TextView txt_product_dialog=itemView.findViewById(R.id.txt_cart_product_name);
        TextView txt_product_price=itemView.findViewById(R.id.txt_cart_product_price);
        TextView txt_company=itemView.findViewById(R.id.txt_company);

        Picasso.get()
                .load(products.get(position).img)
                .into(img_product_dialog);

        txt_product_dialog.setText(new StringBuilder(products.get(position).name).append(" x")
                .append(number).toString());

        txt_company.setText(company_name);


        double price=(Double.parseDouble(products.get(position).price)*Double.parseDouble(number));

       // final double finalPrice = Math.round(price);

        txt_product_price.setText(new StringBuilder("$").append(price));

        builder.setNegativeButton("CONFIRM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

                try {
                    //Add to sqlite
                    //create new cartItem
                    Cart cartItem = new Cart();
                    cartItem.id=products.get(position).id;
                    cartItem.name = products.get(position).name;
                    cartItem.amount = Integer.parseInt(number);
                    cartItem.company = products.get(position).company_name;
                    cartItem.price = price;
                    cartItem.link=products.get(position).img;
                    cartItem.companyId=Integer.parseInt(products.get(position).company_id);

                    Common.cartRepository.insertToCart(cartItem);

                    Navigation_BasedActivity.updateCartCount();
                    Toast.makeText(context, new Gson().toJson(cartItem), Toast.LENGTH_SHORT).show();
                }
                catch (Exception ex){
                    Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

        });


        builder.setView(itemView);
        builder.show();
    }


    @Override
    public int getItemCount() {
        return products.size();
    }



}
