package com.Ali.PharmacistsApp.Utils;

import com.Ali.PharmacistsApp.Database.DataStore.CartRepository;
import com.Ali.PharmacistsApp.Database.DataStore.FavoriteRepository;
import com.Ali.PharmacistsApp.Database.Local.AliRoomDatabase;
import com.Ali.PharmacistsApp.Model.Company;
import com.Ali.PharmacistsApp.Model.Menu;
import com.Ali.PharmacistsApp.Model.Order;
import com.Ali.PharmacistsApp.Model.Product;
import com.Ali.PharmacistsApp.Model.ProductWithCompany;
import com.Ali.PharmacistsApp.Model.User;
import com.Ali.PharmacistsApp.Retrofit.Api;
import com.Ali.PharmacistsApp.Retrofit.FCMClient;
import com.Ali.PharmacistsApp.Retrofit.IFCMService;
import com.Ali.PharmacistsApp.Retrofit.RetrofitClient;

import java.util.List;

public class Common {
    public static final String BASE_URL="http://192.168.137.1:8000/";

    public static final String BASE_URL_IMAGE="http://192.168.137.1:8000/storage/Pharmacy/";

    public static User currentUser=null;
    public static Company currentCompany=null;
    public static Menu currentMenu=null;
    public static Order currentOrder=null;
    public static Product currentProduct=null;
    public static String currentItem="0";


    public static AliRoomDatabase aliRoomDatabase;
    public static CartRepository cartRepository;
    public static FavoriteRepository favoriteRepository;


    public static Api getApi(){
        return RetrofitClient.getClient(BASE_URL).create(Api.class);
    }

    public static final String FCM_API ="https://fcm.googleapis.com/" ;

    public static IFCMService getFCMService()
    {
        return FCMClient.getClient(FCM_API).create(IFCMService.class);
    }

    public static String convertCodeToStatus(int orderStatus) {
        switch (orderStatus)
        {
            case 0:
                return "placed";
            case 1:
                return "Processing";
            case 2:
                return "Shipping";
            case 3:
                return "Shipped";
            case -1:
                return "Cancelled";

            default:
                return "Order Error";
        }
    }
}


// public static final String BASE_URL="http://192.168.1.222:8000/";
// public static final String BASE_URL="http://192.168.43.2:8000/";
//php artisan serve --host=192.168.1.222 --port=8000