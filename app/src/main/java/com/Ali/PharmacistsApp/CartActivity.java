package com.Ali.PharmacistsApp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.Ali.PharmacistsApp.Adapter.CartAdapter;
import com.Ali.PharmacistsApp.Database.ModelDB.Cart;
import com.Ali.PharmacistsApp.Model.DataMessage;
import com.Ali.PharmacistsApp.Model.MyResponse;
import com.Ali.PharmacistsApp.Model.Notifications;
import com.Ali.PharmacistsApp.Model.Order;
import com.Ali.PharmacistsApp.Model.Product;
import com.Ali.PharmacistsApp.Model.User;
import com.Ali.PharmacistsApp.Retrofit.Api;
import com.Ali.PharmacistsApp.Retrofit.IFCMService;
import com.Ali.PharmacistsApp.Utils.Common;
import com.Ali.PharmacistsApp.Utils.RecyclerItemTouchHelper;
import com.Ali.PharmacistsApp.Utils.RecyclerItemTouchHelperListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity implements RecyclerItemTouchHelperListener {

    RecyclerView recycler_cart;
    Button btn_place_order;
    public List<Cart> cartList=new ArrayList<>();
    RelativeLayout rootLayout;
    Api mservice;
    CartAdapter cartAdapter;
    CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        getSupportActionBar().setTitle("Your Cart");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        compositeDisposable = new CompositeDisposable();
        mservice = Common.getApi();

        rootLayout=findViewById(R.id.rootLayout);
        recycler_cart = findViewById(R.id.recycler_cart);
        recycler_cart.setLayoutManager(new LinearLayoutManager(CartActivity.this));
        recycler_cart.setHasFixedSize(true);

        ItemTouchHelper.SimpleCallback simpleCallback=new RecyclerItemTouchHelper(0,ItemTouchHelper.LEFT,this);
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(recycler_cart);

        //sendNotificationToServer();

        btn_place_order = findViewById(R.id.btn_place_order);
        btn_place_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Common.cartRepository.countCartItems()>0) {
                    placeOrder();
                }else{
                    Toast.makeText(CartActivity.this, "Your Cart Is Empty..", Toast.LENGTH_SHORT).show();
                }
            }
        });

        loadCartItems();

    }

    private void loadCartItems() {
        compositeDisposable.add(Common.cartRepository.getCartItem()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<Cart>>() {
                    @Override
                    public void accept(List<Cart> carts) throws Exception {
                        displayCartItem(carts);
                    }
                }));
    }

    private void displayCartItem(List<Cart> carts) {
        cartList=carts;
        cartAdapter= new CartAdapter(carts, this);
        recycler_cart.setAdapter(cartAdapter);

    }



    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof CartAdapter.CartViewHolder)
        {
            String name=cartList.get(viewHolder.getAdapterPosition()).name;

            Cart deletedItem=cartList.get(viewHolder.getAdapterPosition());
            int deletedIndex =viewHolder.getAdapterPosition();

            //delete item from adapter
            cartAdapter.removeItem(deletedIndex);

            //delete from room database
            Common.cartRepository.deleteToCart(deletedItem);


            Snackbar snackbar=Snackbar.make(rootLayout,new StringBuilder(name).append(" removed from Cart List").toString(),
                    Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cartAdapter.restoreItem(deletedItem,deletedIndex);
                    Common.cartRepository.insertToCart(deletedItem);
                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
        }
    }



    private void placeOrder() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Submit Order");

        View submit_order_layout= LayoutInflater.from(this).inflate(R.layout.submit_order_layout,null);
        EditText edt_comment=submit_order_layout.findViewById(R.id.edt_comment);
        EditText edt_other_address=submit_order_layout.findViewById(R.id.edt_other_address);

        RadioButton rdi_user_address=submit_order_layout.findViewById(R.id.rdi_user_address);
        RadioButton rdi_other_address=submit_order_layout.findViewById(R.id.rdi_other_address);

        RadioButton rdi_credit_card=submit_order_layout.findViewById(R.id.rdi_credit_card);
        RadioButton rdi_cod=submit_order_layout.findViewById(R.id.rdi_cod);

        rdi_user_address.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    edt_other_address.setEnabled(false);
            }
        });

        rdi_other_address.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    edt_other_address.setEnabled(true);
            }
        });

        builder.setView(submit_order_layout);
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setPositiveButton("SUBMIT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (rdi_credit_card.isChecked()) {
                    final String orderComment = edt_comment.getText().toString();
                    final String orderAddress;
                    if (rdi_user_address.isChecked())
                        orderAddress = null ;//Common.currentUser.getAddress();
                    else if (rdi_other_address.isChecked())
                        orderAddress = edt_other_address.getText().toString();

                    else
                        orderAddress = "";

                    //submit order to server
                    compositeDisposable.add(
                            Common.cartRepository.getCartItem()
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribeOn(Schedulers.io())
                                    .subscribe(new Consumer<List<Cart>>() {
                                        @Override
                                        public void accept(List<Cart> carts) throws Exception {
                                            if (!TextUtils.isEmpty(orderAddress)) {
                                                //send_order(carts,orderComment,orderAddress,"COD");
                                            }
                                            else
                                                Toast.makeText(CartActivity.this, "Order Address Can't null", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                    );
                }

                else if (rdi_cod.isChecked()){
                    final String orderComment = edt_comment.getText().toString();
                    final String orderAddress;
                    if (rdi_user_address.isChecked())
                        orderAddress = "Address";//Common.currentUser.getAddress();
                    else if (rdi_other_address.isChecked())
                        orderAddress = edt_other_address.getText().toString();

                    else
                        orderAddress = "";

                    //submit order to server
                    compositeDisposable.add(
                            Common.cartRepository.getCartItem()
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribeOn(Schedulers.io())
                                    .subscribe(new Consumer<List<Cart>>() {
                                        @Override
                                        public void accept(List<Cart> carts) throws Exception {
                                            if (!TextUtils.isEmpty(orderAddress)) {
                                                send_order(carts,orderComment,orderAddress,"COD");
                                            }
                                            else
                                                Toast.makeText(CartActivity.this, "Order Address Can't null", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                    );
                }
            }
        });
        builder.show();
    }

    private void send_order(List<Cart> carts,String Comment,String Address,String paymentMethod){
        if (carts.size()>0) {
            for (int i = 0; i < carts.size(); i++) {
                Cart tempCart = carts.get(i);
                String orderDetail =  new Gson().toJson(tempCart);

                mservice.check_quantity_product(Integer.parseInt(tempCart.id),tempCart.amount).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.body().equals("Successfully")){

                            mservice.send_order(Common.currentUser.getEmail(), String.valueOf(tempCart.companyId), orderDetail , 0,
                                    (float) tempCart.price, Comment, Address, paymentMethod
                            ).enqueue(new Callback<Order>() {
                                @Override
                                public void onResponse(@NonNull Call<Order> call, @NonNull Response<Order> response) {
                                    sendNotificationToServer(response.body(),tempCart);
                                    Toast.makeText(getApplicationContext(), new StringBuilder().append("order ").append(tempCart.name).append(" submitted successfully").toString(), Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onFailure(@NonNull Call<Order> call, @NonNull Throwable t) {
                                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                        else {
                            Toast.makeText(CartActivity.this,response.body().toString(),Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(CartActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
        Common.cartRepository.emptyCart();
    }




    private void sendNotificationToServer(Order order,Cart cart) {

        mservice.getToken(order.getStoreId())
                .enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        //when we have token ,just send notification to this token
                        Map<String,String> contentSend=new HashMap<>();
                        contentSend.put("title","New order # From Email:  "+order.getUserEmail());
                        contentSend.put("message","You have new order "+order.getOrderComment());

                        DataMessage dataMessage=new DataMessage();
                        if (response.body().getApi_token()!=null)
                            dataMessage.setTo(response.body().getApi_token());
                        dataMessage.setData(contentSend);

                        IFCMService ifcmService=Common.getFCMService();
                        ifcmService.sendNotification(dataMessage)
                                .enqueue(new Callback<MyResponse>() {
                                    @Override
                                    public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                                        if (response.code()==200)
                                        {
                                            if (response.body().success==1)
                                            {
                                                Toast.makeText(CartActivity.this, "Thank you ,Order Place", Toast.LENGTH_SHORT).show();

                                                //Common.cartRepository.emptyCart();
                                                //finish();
                                            }
                                            else {
                                                Toast.makeText(CartActivity.this, "Send Notification Failed !", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<MyResponse> call, Throwable t) {
                                        Toast.makeText(CartActivity.this, ""+t.getMessage(),Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(CartActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        mservice.notifications(Common.currentUser.getEmail(),cart.link,
                String.valueOf(cart.companyId),"You have new order from email "+order.getUserEmail(),
                cart.name,String.valueOf(cart.amount),String.valueOf(cart.price))
        .enqueue(new Callback<Notifications>() {
            @Override
            public void onResponse(Call<Notifications> call, Response<Notifications> response) {
                Toast.makeText(CartActivity.this, "Inserted Notification Successfully", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Notifications> call, Throwable t) {
                Toast.makeText(CartActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    @Override
    public void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    @Override
    protected void onResume() {
        loadCartItems();
        super.onResume();
    }
}