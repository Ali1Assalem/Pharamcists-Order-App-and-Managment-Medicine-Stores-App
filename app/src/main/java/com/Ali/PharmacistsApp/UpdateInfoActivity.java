package com.Ali.PharmacistsApp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

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

public class UpdateInfoActivity extends AppCompatActivity {


    TextInputLayout tilEmail,tilname,til_password,til_confirm_password;
    EditText etPassword,etEmail,et_name,et_confirm_password;
    MaterialButton bt_save;
    Api mservice;
    SharedPrefManger sharedPrefManger;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_info);

        getSupportActionBar().setTitle("Update Your info");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sharedPrefManger=new SharedPrefManger(UpdateInfoActivity.this);
        mservice = Common.getApi();


        tilEmail = findViewById(R.id.tilemail);
        tilname = findViewById(R.id.tilname);
        til_password = findViewById(R.id.til_password);
        til_confirm_password = findViewById(R.id.til_confirm_password);
        etEmail = findViewById(R.id.et_email);
        et_name = findViewById(R.id.et_name);
        etPassword = findViewById(R.id.et_password);
        et_confirm_password = findViewById(R.id.et_confirm_password);
        bt_save = findViewById(R.id.bt_save);


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

        et_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!et_name.getText().toString().isEmpty()){
                    tilname.setErrorEnabled(false);
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

        et_confirm_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (et_confirm_password.getText().toString().equals(etPassword.getText().toString())) {
                    til_confirm_password.setErrorEnabled(false);
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

        tilname.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_name.getText().clear();
            }
        });


        bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()){
                    update_user_info();
                }
            }
        });

    }
    private boolean validate (){
        if (etEmail.getText().toString().isEmpty()){
            tilEmail.setErrorEnabled(true);
            tilEmail.setError("Email is Required");
            return false;
        }
        if (et_name.getText().toString().isEmpty()){
            tilname.setErrorEnabled(true);
            tilname.setError("Name is Required");
            return false;
        }
        if (etPassword.getText().toString().length()<8 ){
            til_password.setErrorEnabled(true);
            til_password.setError("Required at least 8 characters");
            return false;
        }
        if (!et_confirm_password.getText().toString().equals(etPassword.getText().toString())){
            til_confirm_password.setErrorEnabled(true);
            til_confirm_password.setError("Password does not match");
            return false;
        }
        return true;
    }


    private void update_user_info() {
        mservice.update_user_info(Common.currentUser.getEmail(),
                etPassword.getText().toString(),et_name.getText().toString(),etEmail.getText().toString())
                .enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                            Common.currentUser=response.body();
                            assert response.body() != null;
                            sharedPrefManger.saveUser(response.body());
                            Toast.makeText(UpdateInfoActivity.this,"Updated Successsfully...", Toast.LENGTH_SHORT).show();
                            //
                            showAlertSuccess();


                    }
                    @Override
                    public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void showAlertSuccess(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        View layoutView=getLayoutInflater().inflate(R.layout.my_success_dialog,null);
        AppCompatButton dialogButton=layoutView.findViewById(R.id.bt_ok);
        builder.setView(layoutView);
        AlertDialog alertDialog=builder.create();
        alertDialog.show();

        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                et_name.getText().clear();
                etEmail.getText().clear();
                etPassword.getText().clear();
                et_confirm_password.getText().clear();
                startActivity(new Intent(UpdateInfoActivity.this,Navigation_BasedActivity.class));
                finish();
            }
        });

    }
}