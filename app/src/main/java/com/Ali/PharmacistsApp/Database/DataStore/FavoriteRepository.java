package com.Ali.PharmacistsApp.Database.DataStore;

import com.Ali.PharmacistsApp.Database.ModelDB.Favorite;

import java.util.List;

import io.reactivex.Flowable;

public class FavoriteRepository implements IFavoriteDataSource {

    private IFavoriteDataSource favoriteDataSource;

    public FavoriteRepository(IFavoriteDataSource favoriteDataSource) {
        this.favoriteDataSource = favoriteDataSource;
    }

    private static FavoriteRepository instance;
    public static FavoriteRepository getInstance(IFavoriteDataSource favoriteDataSource){
        if(instance==null)
            instance=new FavoriteRepository(favoriteDataSource);
        return instance;
    }

    @Override
    public void emptyFavorite() {
        favoriteDataSource.emptyFavorite();
    }

    @Override
    public Flowable<List<Favorite>> getFavItems() {
        return favoriteDataSource.getFavItems();
    }

    @Override
    public int isFavorite(int itemId) {
        return favoriteDataSource.isFavorite(itemId);
    }

    @Override
    public void insertFav(Favorite... favorites) {
        favoriteDataSource.insertFav(favorites);
    }

    @Override
    public void delete(Favorite favorite) {
        favoriteDataSource.delete(favorite);
    }
}
