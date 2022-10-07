package com.Ali.PharmacistsApp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;

@SuppressLint("CustomSplashScreen")
public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        desplaySplachScreen();

    }

    @SuppressLint("CheckResult")
    private void desplaySplachScreen(){
        Completable.timer(2, TimeUnit.SECONDS,
                AndroidSchedulers.mainThread())
                .subscribe(new Action() {
                    @Override
                    public void run() throws Exception {
                        startActivity(new Intent(SplashScreenActivity.this, RegisterActivity.class));
                        finish();
                    }
                });
    }
}