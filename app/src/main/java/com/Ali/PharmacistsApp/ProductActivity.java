package com.Ali.PharmacistsApp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.Ali.PharmacistsApp.Adapter.ProductAdapter;
import com.Ali.PharmacistsApp.Model.Product;
import com.Ali.PharmacistsApp.Retrofit.Api;
import com.Ali.PharmacistsApp.Utils.Common;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ProductActivity extends AppCompatActivity {
    SwipeRefreshLayout swipeRefreshLayout;

    Api mservice;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    RecyclerView lst_product;
    ProductAdapter productAdapter,searchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        getSupportActionBar().setTitle("Medicines in : " + Common.currentMenu.name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mservice = Common.getApi();

        lst_product = findViewById(R.id.recycler_products);
        lst_product.setLayoutManager(new GridLayoutManager(this, 2));
        lst_product.setHasFixedSize(true);


        swipeRefreshLayout = findViewById(R.id.swipe_to_refresh);


        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);

                LoadListDrink(Common.currentMenu.id, Common.currentCompany.id);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);

                LoadListDrink(Common.currentMenu.id, Common.currentCompany.id);
            }
        });

    }

    private void LoadListDrink(float menuId, float companyid) {
        compositeDisposable.add(mservice.getProductByMenuID(menuId, companyid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Product>>() {
                    @Override
                    public void accept(List<Product> products) throws Exception {
                        displayDrinkList(products);
                    }
                }));
    }

    private void displayDrinkList(List<Product> productList) {
        productAdapter = new ProductAdapter(productList,this);
        lst_product.setAdapter(productAdapter);

        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);

        MenuItem menuItem = menu.findItem(R.id.search);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        //Event
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                startSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        menuItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                //restore to original adapter when use close search
                lst_product.setAdapter(productAdapter);
                return true;
            }
        });
        return true;
    }

    private void startSearch(String query) {

         compositeDisposable.add(mservice.searchProduct(query,Common.currentMenu.id,Common.currentCompany.id)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<List<Product>>() {
            @Override
            public void accept(List<Product> productList) throws Exception {
                searchAdapter=new ProductAdapter(productList,ProductActivity.this);
                lst_product.setAdapter(searchAdapter);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Toast.makeText(ProductActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }));
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