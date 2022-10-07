package com.Ali.PharmacistsApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.Ali.PharmacistsApp.Model.User;
import com.Ali.PharmacistsApp.Retrofit.Api;
import com.Ali.PharmacistsApp.Utils.Common;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingActivity extends AppCompatActivity {

    TextInputLayout tilEmail,til_password;
    EditText etPassword,etEmail;
    MaterialButton bt_match;
    Api mservice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        getSupportActionBar().setTitle("Matching Information");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mservice = Common.getApi();

        tilEmail = findViewById(R.id.tilemail);
        til_password = findViewById(R.id.til_password);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        bt_match = findViewById(R.id.bt_match);


        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!etEmail.getText().toString().isEmpty()){
                    tilEmail.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etPassword.getText().toString().length() > 7) {
                    til_password.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        tilEmail.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etEmail.getText().clear();
            }
        });


        bt_match.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()){
                    matching();
                }
            }
        });

    }
    private boolean validate (){
        if (etPassword.getText().toString().length()<8 ){
            til_password.setErrorEnabled(true);
            til_password.setError("Required at least 8 characters");
            return false;
        }
        if (etEmail.getText().toString().isEmpty()){
            tilEmail.setErrorEnabled(true);
            tilEmail.setError("Email is Required");
            return false;
        }
        return true;
    }

    private void matching(){
        mservice.login(etEmail.getText().toString().trim(),etPassword.getText().toString().trim())
                .enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        User user=response.body();
                        if(TextUtils.isEmpty(user.getSuccess())){
                            Toast.makeText(SettingActivity.this, "Matching Successfully...", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(getBaseContext(), UpdateInfoActivity.class);
                            intent.putExtra("password", etPassword.getText().toString().trim());
                            startActivity(intent);
                            finish();

                        }else {
                            Toast.makeText(SettingActivity.this, "Wrong data...", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(SettingActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}