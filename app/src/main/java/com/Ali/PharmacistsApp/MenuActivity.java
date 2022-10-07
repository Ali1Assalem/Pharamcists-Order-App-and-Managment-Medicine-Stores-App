package com.Ali.PharmacistsApp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;

import com.Ali.PharmacistsApp.Adapter.MenuAdapter;
import com.Ali.PharmacistsApp.Model.Menu;
import com.Ali.PharmacistsApp.Retrofit.Api;
import com.Ali.PharmacistsApp.Utils.Common;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MenuActivity extends AppCompatActivity {
    SwipeRefreshLayout swipeRefreshLayout;
    Api mservice;
    CompositeDisposable compositeDisposable=new CompositeDisposable();
    RecyclerView lstMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        getSupportActionBar().setTitle("Menues "+Common.currentCompany.name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        swipeRefreshLayout=findViewById(R.id.swipe_to_refresh);


        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);

                getMenuByCompanyID();
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);

                getMenuByCompanyID();
            }
        });


        mservice= Common.getApi();
        lstMenu=findViewById(R.id.lst_menu);
        lstMenu.setLayoutManager(new GridLayoutManager(this, 2));
        lstMenu.setHasFixedSize(true);

    }

    private void getMenuByCompanyID() {
        compositeDisposable.add(mservice.getMenuByCompanyID(Common.currentCompany.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Menu>>() {
                    @Override
                    public void accept(List<Menu> menus) throws Exception {
                        displayMenu(menus);
                    }
                }));
    }


    private void displayMenu(List<Menu> menus) {
        MenuAdapter menuAdapter =new MenuAdapter(this,menus);
        lstMenu.setAdapter(menuAdapter);
        swipeRefreshLayout.setRefreshing(false);
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