package com.Ali.PharmacistsApp.Database.Local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.Ali.PharmacistsApp.Database.ModelDB.Cart;
import com.Ali.PharmacistsApp.Database.ModelDB.Favorite;

@Database(entities = {Cart.class, Favorite.class},version = 1,exportSchema = false)
public abstract class AliRoomDatabase extends RoomDatabase {
    private static AliRoomDatabase instance;
    public abstract CartDAO cartDAO();
    public abstract FavoriteDao favoriteDao();

    public static AliRoomDatabase getInstance(Context context)
    {
        if (instance==null)
            instance=Room.databaseBuilder(context, AliRoomDatabase.class,"ali")
                    .allowMainThreadQueries()
                    .build();
        return instance;
    }
}
