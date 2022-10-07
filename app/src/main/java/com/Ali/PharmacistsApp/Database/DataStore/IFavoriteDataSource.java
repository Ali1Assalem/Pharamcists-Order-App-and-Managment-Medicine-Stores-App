package com.Ali.PharmacistsApp.Database.DataStore;

import com.Ali.PharmacistsApp.Database.ModelDB.Favorite;

import java.util.List;

import io.reactivex.Flowable;

public interface IFavoriteDataSource {

    Flowable<List<Favorite>> getFavItems();

    int isFavorite(int itemId);

    void insertFav(Favorite...favorites);

    void delete(Favorite favorite);

    void emptyFavorite();

}
