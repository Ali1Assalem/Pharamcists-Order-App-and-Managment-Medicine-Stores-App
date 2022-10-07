package com.Ali.PharmacistsApp.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.Ali.PharmacistsApp.Adapter.CompanyAdapter;
import com.Ali.PharmacistsApp.Adapter.ProductAdapter;
import com.Ali.PharmacistsApp.CartActivity;
import com.Ali.PharmacistsApp.Database.DataStore.CartRepository;
import com.Ali.PharmacistsApp.Database.DataStore.FavoriteRepository;
import com.Ali.PharmacistsApp.Database.Local.AliRoomDatabase;
import com.Ali.PharmacistsApp.Database.Local.CartDataSource;
import com.Ali.PharmacistsApp.Database.Local.FavoriteDataSource;
import com.Ali.PharmacistsApp.Model.Banner;
import com.Ali.PharmacistsApp.Model.Company;
import com.Ali.PharmacistsApp.Model.Product;
import com.Ali.PharmacistsApp.Model.ProductWithCompany;
import com.Ali.PharmacistsApp.Navigation_BasedActivity;
import com.Ali.PharmacistsApp.R;
import com.Ali.PharmacistsApp.Retrofit.Api;
import com.Ali.PharmacistsApp.Utils.Common;
import com.nex3z.notificationbadge.NotificationBadge;

import java.util.HashMap;
import java.util.List;

import edmt.dev.edmtslider.SliderLayout;
import edmt.dev.edmtslider.SliderTypes.BaseSliderView;
import edmt.dev.edmtslider.SliderTypes.TextSliderView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class HomeFragment extends Fragment {

    SwipeRefreshLayout swipeRefreshLayout;
    Api mservice;
    SliderLayout sliderLayout;
    CompositeDisposable compositeDisposable=new CompositeDisposable();
    RecyclerView lst_company,lst_product;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        mservice= Common.getApi();
        lst_company=root.findViewById(R.id.lst_company);
        lst_company.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false));
        lst_company.setHasFixedSize(true);

        lst_product=root.findViewById(R.id.lst_product);
        lst_product.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false));
        lst_product.setHasFixedSize(true);

        sliderLayout=root.findViewById(R.id.slider);


        swipeRefreshLayout=root.findViewById(R.id.swipe_to_refresh);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                getCompany();
                getProductHighRate();
            }
        });


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                getCompany();
                getProductHighRate();
            }
        });

        getBannerImage();
        initDB();
        return root;
    }


    private void initDB() {
        Common.aliRoomDatabase= AliRoomDatabase.getInstance(getActivity().getApplicationContext());
        Common.cartRepository= CartRepository.getInstance(CartDataSource.getInstance(Common.aliRoomDatabase.cartDAO()));
        Common.favoriteRepository= FavoriteRepository.getInstance(FavoriteDataSource.getInstance(Common.aliRoomDatabase.favoriteDao()));

    }

    private void getProductHighRate(){
        compositeDisposable.add(mservice.getProductHighRate()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<List<Product>>() {
            @Override
            public void accept(List<Product> products) throws Exception {
                displayProducts(products);
            }
        }));
    }

    private void displayProducts(List<Product> products) {
        ProductAdapter productAdapter =new ProductAdapter(products,getContext());
        lst_product.setAdapter(productAdapter);
        swipeRefreshLayout.setRefreshing(false);
    }


    private void getCompany() {
        compositeDisposable.add(mservice.getCompany()
                .subscribeOn(Schedulers.io())  //upStream  observable
                .observeOn(AndroidSchedulers.mainThread())  //downStream  observer
                .subscribe(new Consumer<List<Company>>() {
                    @Override
                    public void accept(List<Company> companies) throws Exception {
                        displayCompany(companies);
                    }
                }));
    }


    private void displayCompany(List<Company> companies) {
        CompanyAdapter menuAdapter =new CompanyAdapter(getContext(),companies);
        lst_company.setAdapter(menuAdapter);
        swipeRefreshLayout.setRefreshing(false);
    }



    private void getBannerImage() {
        compositeDisposable.add(mservice.getBanners()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Banner>>() {
                    @Override
                    public void accept(List<Banner> banners) throws Exception {
                        displayImages(banners);
                    }
                }));
    }


    private void displayImages(List<Banner> banners){
        HashMap<String,String> bannerMap=new HashMap<>();
        for (Banner item:banners)
            bannerMap.put(item.getName(),item.getLink());

        for (String name:bannerMap.keySet()){
            TextSliderView textSliderView=new TextSliderView(getContext());
            textSliderView.description(name)
                    .image(bannerMap.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit);

            sliderLayout.addSlider(textSliderView);
        }
    }

    @Override
    public void onDestroy() {
        compositeDisposable.dispose();
        super.onDestroy();
    }

}