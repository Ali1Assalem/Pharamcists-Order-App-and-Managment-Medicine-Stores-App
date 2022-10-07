package com.Ali.PharmacistsApp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.Ali.PharmacistsApp.Model.User;
import com.Ali.PharmacistsApp.Retrofit.Api;
import com.Ali.PharmacistsApp.Utils.Common;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.messaging.FirebaseMessaging;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity extends AppCompatActivity {
    public TextInputLayout layoutEmail, layoutPassword;
    private TextInputEditText txtEmail, txtPassword;
    private TextView txtSignUp;
    private Button btnSignIn;
    private ProgressDialog dialog;
    SharedPrefManger sharedPrefManger;
    Api mservice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        init();
        sharedPrefManger=new SharedPrefManger(SignInActivity.this);
        mservice = Common.getApi();

    }
    private void init() {
        layoutPassword = findViewById(R.id.txtLayoutPasswordSignIn);
        layoutEmail = findViewById(R.id.txtLayoutEmailSignIn);
        txtPassword = findViewById(R.id.txtPasswordSignIn);
        txtSignUp = findViewById(R.id.txtSignUp);
        txtEmail = findViewById(R.id.txtEmailSignIn);
        btnSignIn = findViewById(R.id.btnSignIn);
        dialog = new ProgressDialog(getApplicationContext());
        dialog.setCancelable(false);



        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignInActivity.this, RegisterActivity.class));
            }
        });

        btnSignIn.setOnClickListener(v -> {
            //validate fields first
            if (validate()) {

                login();

            }
        });

        txtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!txtEmail.getText().toString().isEmpty()) {
                    layoutEmail.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        txtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (txtPassword.getText().toString().length() > 7) {
                    layoutPassword.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private boolean validate() {
        if (txtEmail.getText().toString().isEmpty()) {
            layoutEmail.setErrorEnabled(true);
            layoutEmail.setError("Email is Required");
            return false;
        }
        if (txtPassword.getText().toString().length() < 8) {
            layoutPassword.setErrorEnabled(true);
            layoutPassword.setError("Required at least 8 characters");
            return false;
        }
        return true;
    }

    private void login() {
        mservice.login(txtEmail.getText().toString(),
                txtPassword.getText().toString())
                .enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        User user=response.body();
                        /* getSuccess() return from server "wron data" something wrong  in login or register */
                        if(TextUtils.isEmpty(user.getSuccess())){
                            Common.currentUser=response.body();
                            sharedPrefManger.saveUser(response.body());
                            updateTokenToServer();
                            Toast.makeText(SignInActivity.this, "Login Successfully...", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignInActivity.this, Navigation_BasedActivity.class));
                            finish();
                        }
                        else {
                            Toast.makeText(SignInActivity.this, "Wrong data...", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(SignInActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (sharedPrefManger.isLoggedIn()){
            startActivity(new Intent(SignInActivity.this, Navigation_BasedActivity.class));
            SignInActivity.this.finish();

            Common.currentUser=sharedPrefManger.getUser();

        }
    }

    private void updateTokenToServer() {
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                mservice.updateToken(Common.currentUser.getEmail(),task.getResult())
                        .enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                Toast.makeText(SignInActivity.this, response.body(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                Toast.makeText(SignInActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });

    }

}