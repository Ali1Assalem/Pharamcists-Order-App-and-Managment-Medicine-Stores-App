package com.Ali.PharmacistsApp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.Ali.PharmacistsApp.Adapter.CartAdapter;
import com.Ali.PharmacistsApp.Adapter.NotificationAdapter;
import com.Ali.PharmacistsApp.Database.ModelDB.Cart;
import com.Ali.PharmacistsApp.Database.ModelDB.Favorite;
import com.Ali.PharmacistsApp.Model.Menu;
import com.Ali.PharmacistsApp.Model.Notifications;
import com.Ali.PharmacistsApp.Retrofit.Api;
import com.Ali.PharmacistsApp.Utils.Common;

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

public class NotificationsActivity extends AppCompatActivity {
    RecyclerView recycler_notifications;
    Api mservice;
    CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        getSupportActionBar().setTitle("Notifications");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        compositeDisposable = new CompositeDisposable();
        mservice = Common.getApi();

        recycler_notifications=findViewById(R.id.recycler_notifications);
        recycler_notifications.setLayoutManager(new LinearLayoutManager(this));
        recycler_notifications.setHasFixedSize(true);

        loadNotifications(Common.currentUser.getEmail());
    }

    private void loadNotifications(String email) {
        compositeDisposable.add(mservice.getNotificationsByTo(email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Notifications>>() {
                    @Override
                    public void accept(List<Notifications> notifications) throws Exception {
                        displayNotifications(notifications);
                    }
                }));
    }

    private void displayNotifications(List<Notifications> notifications) {
        Collections.reverse(notifications);
        NotificationAdapter notificationAdapter=new NotificationAdapter(notifications,this);
        recycler_notifications.setAdapter(notificationAdapter);
    }


    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}