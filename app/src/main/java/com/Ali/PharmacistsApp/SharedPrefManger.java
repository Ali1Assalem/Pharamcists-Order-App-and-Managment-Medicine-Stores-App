package com.Ali.PharmacistsApp;

import android.content.Context;
import android.content.SharedPreferences;

import com.Ali.PharmacistsApp.Model.User;


public class SharedPrefManger {
    private static String SHARED_PREF_NAME="ali";
    private SharedPreferences sharedPreferences;
    Context context;
    private SharedPreferences.Editor editor;

    public SharedPrefManger(Context context) {
        this.context = context;
    }

    public void saveUser(User user){
        sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        editor.putString("email",user.getEmail());
        editor.putString("name",user.getName());
        editor.putString("image",user.getImage());
        editor.putInt("id", user.getId());
        editor.putString("api_token",user.getApi_token());
        editor.putBoolean("looged",true);
        editor.apply();
    }

    public void setImage(String image){
        SharedPreferences sharedPreferences=context.getSharedPreferences("ali",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("image",image);
        editor.apply();
    }

    public String getImage(Context context){
        SharedPreferences sp=context.getSharedPreferences("ali",Context.MODE_PRIVATE);
        String imagePath=sp.getString("image","");
        return imagePath;
    }

    public boolean isLoggedIn(){
        sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("looged",false);
    }

    public void DeleteUser(){
        sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        editor.putString("email",null);
        editor.putString("name",null);
        editor.putString("image",null);
        editor.putInt("id", -1);
        editor.putString("api_token",null);
        editor.putBoolean("looged",false);
        editor.apply();
    }

    public User getUser(){
        sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return new User(sharedPreferences.getString("name",null),
                sharedPreferences.getString("email",null),
                sharedPreferences.getString("api_token",null),
                sharedPreferences.getString("updated_at",null),
                sharedPreferences.getString("created_at",null),
                sharedPreferences.getString("image",null),
                sharedPreferences.getInt("id",-1),
                sharedPreferences.getString("success",null));

    }



}
