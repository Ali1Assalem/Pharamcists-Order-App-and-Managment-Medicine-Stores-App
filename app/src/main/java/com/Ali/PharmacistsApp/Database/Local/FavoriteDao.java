package com.Ali.PharmacistsApp.Database.Local;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import com.Ali.PharmacistsApp.Database.ModelDB.Favorite;

import java.util.List;
import io.reactivex.Flowable;

@Dao
public interface FavoriteDao  {
@Query("SELECT * FROM Favorite")
    Flowable<List<Favorite>> getFavItems();

@Query("SELECT EXISTS (SELECT 1 FROM Favorite WHERE id=:itemId)")
    int isFavorite(int itemId);

@Insert
void insertFav(Favorite...favorites);

@Delete
    void delete(Favorite favorite);

    @Query("DELETE FROM Favorite")
    void emptyFavorite();
}
