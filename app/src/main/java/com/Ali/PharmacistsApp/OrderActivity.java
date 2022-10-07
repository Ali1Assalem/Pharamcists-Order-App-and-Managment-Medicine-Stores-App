package com.Ali.PharmacistsApp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.Ali.PharmacistsApp.Adapter.OrderAdapter;
import com.Ali.PharmacistsApp.Model.Order;
import com.Ali.PharmacistsApp.Retrofit.Api;
import com.Ali.PharmacistsApp.Utils.Common;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.Collections;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class OrderActivity extends AppCompatActivity {

    static Api mService;
    static RecyclerView recycler_orders;

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        getSupportActionBar().setTitle("Your Orders");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mService= Common.getApi();

        recycler_orders=findViewById(R.id.recycler_orders);
        recycler_orders.setLayoutManager(new LinearLayoutManager(this));
        recycler_orders.setHasFixedSize(true);

        bottomNavigationView=findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId()==R.id.order_new)
                {
                    loadOrder("0",getApplicationContext());
                    Common.currentItem="0";
                }
                else if (item.getItemId()==R.id.order_cancel)
                {
                    loadOrder("-1",getApplicationContext());
                    Common.currentItem="-1";
                }
                else if (item.getItemId()==R.id.order_processing)
                {
                    loadOrder("1",getApplicationContext());
                    Common.currentItem="1";
                }
                else if (item.getItemId()==R.id.order_shipping)
                {
                    loadOrder("2",getApplicationContext());
                    Common.currentItem="2";
                }
                else if (item.getItemId()==R.id.order_shipped)
                {
                    loadOrder("3",getApplicationContext());
                    Common.currentItem="3";
                }

                return true;
            }
        });


        loadOrder("0",this);
    }

    static CompositeDisposable compositeDisposable=new CompositeDisposable();

    public static void loadOrder(String statusCode, Context context) {
        if (Common.currentUser != null) {
            compositeDisposable.add(mService.getOrder(Common.currentUser.getEmail().toString(), statusCode)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Consumer<List<Order>>() {
                        @Override
                        public void accept(List<Order> orders) throws Exception {
                            displayOrder(orders,context);
                        }
                    }));
        }
        else {
            Toast.makeText(context, "Please logging again !", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }


    private static void displayOrder(List<Order> orders,Context context) {
        Collections.reverse(orders);
        OrderAdapter orderAdapter=new OrderAdapter(orders, context);
        recycler_orders.setAdapter(orderAdapter);
    }




    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        Common.currentItem="0";
        super.onStop();
    }
}