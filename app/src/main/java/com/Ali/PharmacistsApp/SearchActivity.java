package com.Ali.PharmacistsApp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.Ali.PharmacistsApp.Adapter.ProductAdapter;
import com.Ali.PharmacistsApp.Model.Product;
import com.Ali.PharmacistsApp.Retrofit.Api;
import com.Ali.PharmacistsApp.Utils.Common;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SearchActivity extends AppCompatActivity {
    Api mservice;
    RecyclerView recycler_search;

    CompositeDisposable compositeDisposable=new CompositeDisposable();
    EditText inputSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_search);

        ImageView imageBack=findViewById(R.id.imageBack);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SearchActivity.this, Navigation_BasedActivity.class));
            }
        });


        mservice= Common.getApi();

        recycler_search=findViewById(R.id.searchRecyclerView);
        recycler_search.setLayoutManager(new GridLayoutManager(this,2));

        inputSearch=findViewById(R.id.inputSearch);

        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable){
            String str = editable.toString();
            search(str.trim());
            }
    });


    }


    private void search(String name){
        compositeDisposable.add(mservice.search(name)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<List<Product>>() {
            @Override
            public void accept(List<Product> productList) throws Exception {
                    ProductAdapter productAdapter=new ProductAdapter(productList,SearchActivity.this);
                    recycler_search.setAdapter(productAdapter);
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