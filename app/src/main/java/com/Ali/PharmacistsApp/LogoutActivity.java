package com.Ali.PharmacistsApp;

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

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogoutActivity extends AppCompatActivity {

    TextInputLayout tilEmail;
    EditText etPassword,etEmail;
    MaterialButton bt_logout;
    Api mservice;

    SharedPrefManger sharedPrefManger=new SharedPrefManger(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);

        getSupportActionBar().setTitle("Logout");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mservice = Common.getApi();

        tilEmail=findViewById(R.id.tilemail);
        etEmail=findViewById(R.id.et_email);
        etPassword=findViewById(R.id.et_password);
        bt_logout=findViewById(R.id.bt_logout);

        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //check condition
                if (!charSequence.toString().isEmpty()&&charSequence.toString().matches("[a-zA-Z@.+]")){
                    tilEmail.setError("Allow only chracter");
                }else {
                    tilEmail.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        tilEmail.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etEmail.getText().clear();
            }
        });


        bt_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });

    }

    private void logout(){
        mservice.login(etEmail.getText().toString().trim(),etPassword.getText().toString().trim())
                .enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        User user=response.body();
                        if(TextUtils.isEmpty(user.getSuccess())){
                            sharedPrefManger.DeleteUser();
                            Common.favoriteRepository.emptyFavorite();
                            Common.cartRepository.emptyCart();
                            Toast.makeText(LogoutActivity.this, "Logout Successfully...", Toast.LENGTH_SHORT).show();
                            showAlertSuccess();
                        }else {
                            Toast.makeText(LogoutActivity.this, "Wrong data...", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(LogoutActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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
                etEmail.getText().clear();
                etPassword.getText().clear();
                startActivity(new Intent(LogoutActivity.this,RegisterActivity.class));
                finish();
            }
        });

    }

}