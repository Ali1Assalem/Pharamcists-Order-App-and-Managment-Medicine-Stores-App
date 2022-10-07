package com.Ali.PharmacistsApp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.Ali.PharmacistsApp.Database.ModelDB.Cart;
import com.Ali.PharmacistsApp.Model.Comment;
import com.Ali.PharmacistsApp.Model.Menu;
import com.Ali.PharmacistsApp.Retrofit.Api;
import com.Ali.PharmacistsApp.Utils.Common;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailActivity extends AppCompatActivity {

    ImageView imgProduct;
    TextView porduct_price,finalPrice,product_name;
    ElegantNumberButton count;
    FloatingActionButton floatingStar,floatingCart;
    Button showComments;
    RatingBar ratingBar;
    Api mservice;
    CompositeDisposable compositeDisposable=new CompositeDisposable();

    int amount;
    double final_price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_detail);

        getSupportActionBar().setTitle("More Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imgProduct=findViewById(R.id.img_product);
        porduct_price=findViewById(R.id.product_price);
        count=findViewById(R.id.number_button);
        finalPrice=findViewById(R.id.final_price);
        floatingStar=findViewById(R.id.btn_rating);
        floatingCart=findViewById(R.id.btnCart);
        showComments=findViewById(R.id.btn_showComment);
        product_name=findViewById(R.id.product_name);
        ratingBar=findViewById(R.id.ratingBar);

        mservice=Common.getApi();

        getProductRate();

        showComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getComment();
            }
        });

        floatingStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogRating();
            }
        });

        floatingCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToCart();
            }
        });

        Picasso.get()
                .load(Common.currentProduct.img)
                .into(imgProduct);

        porduct_price.setText(Common.currentProduct.price);
        product_name.setText(Common.currentProduct.name);

        amount= Integer.parseInt(count.getNumber());
        final_price=(Double.parseDouble(Common.currentProduct.price))*amount;
        finalPrice.setText(new StringBuilder("$").append(final_price));

        count.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                amount=newValue;
                final_price=(Double.parseDouble(Common.currentProduct.price))*(amount);
                finalPrice.setText(new StringBuilder("$").append(final_price));
            }
        });

    }



    private void showDialogRating() {
        androidx.appcompat.app.AlertDialog.Builder builder=new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Rating Product");
        builder.setMessage("Please fill information");

        View itemView= LayoutInflater.from(ProductDetailActivity.this).inflate(R.layout.raiting_layout,null);

        RatingBar ratingBar=itemView.findViewById(R.id.raiting_bar);
        EditText edt_comment=itemView.findViewById(R.id.edt_comment);

        builder.setView(itemView);
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                float rate = ratingBar.getRating();
                addComment(String.valueOf(rate),edt_comment.getText().toString());
                dialogInterface.dismiss();
            }
        });

        AlertDialog dialog=builder.create();
        dialog.show();
    }


    private void addComment(String rate,String comment){
        mservice.addComment(String.valueOf(Common.currentUser.getId()),Common.currentProduct.id,rate,comment,Common.currentUser.getName())
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Toast.makeText(ProductDetailActivity.this, response.body(), Toast.LENGTH_SHORT).show();
                        getProductRate();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(ProductDetailActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void getComment(){
        compositeDisposable.add(mservice.getComment(Common.currentProduct.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Comment>>() {
                    @Override
                    public void accept(List<Comment> comments) throws Exception {
                        Collections.reverse(comments);
                        displayComments(comments);
                    }
                }));
    }

    private void displayComments(List<Comment> comments) {
        CommentFragmentDialog commentFragmentDialog=new CommentFragmentDialog(comments,ProductDetailActivity.this);

        commentFragmentDialog.show(getSupportFragmentManager(),commentFragmentDialog.getTag());
        if (comments.size()==0)
            Toast.makeText(ProductDetailActivity.this, "No Comments Yet...", Toast.LENGTH_SHORT).show();
    }

    private void addToCart(){
        try {
            //Add to sqlite
            //create new cartItem
            Cart cartItem = new Cart();
            cartItem.id=Common.currentProduct.id;
            cartItem.name = Common.currentProduct.name;
            cartItem.amount = amount;
            cartItem.company = Common.currentProduct.company_name;
            cartItem.price = final_price;
            cartItem.link=Common.currentProduct.img;
            cartItem.companyId=Integer.parseInt(Common.currentProduct.company_id);

            Common.cartRepository.insertToCart(cartItem);
            Toast.makeText(ProductDetailActivity.this, "Added to cart...", Toast.LENGTH_SHORT).show();

            //Toast.makeText(ProductDetailActivity.this, new Gson().toJson(cartItem), Toast.LENGTH_SHORT).show();
        }
        catch (Exception ex){
            Toast.makeText(ProductDetailActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private void getProductRate(){
        mservice.getProductRate(Common.currentProduct.id)
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.body().contains("this product doesnt have rate yet")){
                            //Toast.makeText(getBaseContext(), "this product doesnt have rate yet...", Toast.LENGTH_SHORT).show();
                        }else
                            ratingBar.setRating(Float.parseFloat(response.body()));
                    }
                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(ProductDetailActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

}