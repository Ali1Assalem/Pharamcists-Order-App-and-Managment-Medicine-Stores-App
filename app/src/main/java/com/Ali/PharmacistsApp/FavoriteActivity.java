package com.Ali.PharmacistsApp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.Ali.PharmacistsApp.Adapter.FavoriteAdapter;
import com.Ali.PharmacistsApp.Database.ModelDB.Favorite;
import com.Ali.PharmacistsApp.Utils.Common;
import com.Ali.PharmacistsApp.Utils.RecyclerItemTouchHelper;
import com.Ali.PharmacistsApp.Utils.RecyclerItemTouchHelperListener;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class FavoriteActivity extends AppCompatActivity implements RecyclerItemTouchHelperListener {
    RecyclerView recycler_fav;

    RelativeLayout rootLayout;
    CompositeDisposable compositeDisposable;

    FavoriteAdapter favoriteAdapter;
    List<Favorite> localFavorites=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        getSupportActionBar().setTitle("Saved Medicine");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        compositeDisposable=new CompositeDisposable();

        rootLayout=findViewById(R.id.rootLayout);

        recycler_fav=findViewById(R.id.recycler_fav);
        recycler_fav.setLayoutManager(new LinearLayoutManager(this));
        recycler_fav.setHasFixedSize(true);


        ItemTouchHelper.SimpleCallback simpleCallback=new RecyclerItemTouchHelper(0,ItemTouchHelper.LEFT,this);
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(recycler_fav);

        loadFavoriteItems();
    }

    private void loadFavoriteItems() {
        compositeDisposable.add(Common.favoriteRepository.getFavItems()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<Favorite>>() {
                    @Override
                    public void accept(List<Favorite> favorites) throws Exception {
                        displayFavoriteItems(favorites);
                    }
                }));
    }

    private void displayFavoriteItems(List<Favorite> favorites) {
        localFavorites=favorites;
        favoriteAdapter=new FavoriteAdapter(this,favorites);
        recycler_fav.setAdapter(favoriteAdapter);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof FavoriteAdapter.FavoriteViewHolder)
        {
            String name=localFavorites.get(viewHolder.getAdapterPosition()).name;

            Favorite deletedItem=localFavorites.get(viewHolder.getAdapterPosition());
            int deletedIndex =viewHolder.getAdapterPosition();

            //delete item from adapter
            favoriteAdapter.removeItem(deletedIndex);

            //delete from room database
            Common.favoriteRepository.delete(deletedItem);

            Snackbar snackbar=Snackbar.make(rootLayout,new StringBuilder(name).append("removed from Favorite List").toString(),
                    Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    favoriteAdapter.restoreItem(deletedItem,deletedIndex);
                    Common.favoriteRepository.insertFav(deletedItem);
                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadFavoriteItems();
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