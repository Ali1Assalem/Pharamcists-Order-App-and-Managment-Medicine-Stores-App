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

public class RegisterActivity extends AppCompatActivity {

    private TextInputLayout layoutEmail,layoutPassword,layoutConfirm,layoutName,layoutAddress;
    private TextInputEditText txtEmail,txtPassword,txtConfirm,txtName,txtAddress;
    private TextView txtSignIn;
    private Button btnSignUp;
    private ProgressDialog dialog;
    Api mservice;
    SharedPrefManger sharedPrefManger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mservice= Common.getApi();
        sharedPrefManger=new SharedPrefManger(RegisterActivity.this);
        init();
    }

    private void init() {
        layoutPassword = findViewById(R.id.txtLayoutPasswordSignUp);
        layoutPassword = findViewById(R.id.txtLayoutPasswordSignUp);
        layoutEmail = findViewById(R.id.txtLayoutEmailSignUp);
        layoutName= findViewById(R.id.txtLayoutNameSignUp);
        layoutConfirm = findViewById(R.id.txtLayoutConfrimSignUp);
        txtPassword = findViewById(R.id.txtPasswordSignUp);
        txtConfirm = findViewById(R.id.txtConfirmSignUp);
        txtSignIn = findViewById(R.id.txtSignIn);
        txtEmail = findViewById(R.id.txtEmailSignUp);
        txtName = findViewById(R.id.txtNameSignUp);
        btnSignUp = findViewById(R.id.btnSignUp);
        dialog = new ProgressDialog(getApplicationContext());
        dialog.setCancelable(false);

        txtSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this,SignInActivity.class));
            }
        });


        btnSignUp.setOnClickListener(v->{
            //validate fields first
            if (validate()){

               register();
              //  Toast.makeText(getApplicationContext(),Common.currentUser.getApi_token().toString(), Toast.LENGTH_SHORT).show();

            }
        });
        txtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!txtEmail.getText().toString().isEmpty()){
                    layoutEmail.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        txtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!txtName.getText().toString().isEmpty()){
                    layoutName.setErrorEnabled(false);
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
                if (txtPassword.getText().toString().length()>7){
                    layoutPassword.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        txtConfirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (txtConfirm.getText().toString().equals(txtPassword.getText().toString())){
                    layoutConfirm.setErrorEnabled(false);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private boolean validate (){
        if (txtEmail.getText().toString().isEmpty()){
            layoutEmail.setErrorEnabled(true);
            layoutEmail.setError("Email is Required");
            return false;
        }
        if (txtName.getText().toString().isEmpty()){
            layoutName.setErrorEnabled(true);
            layoutName.setError("Name is Required");
            return false;
        }
        if (txtPassword.getText().toString().length()<8){
            layoutPassword.setErrorEnabled(true);
            layoutPassword.setError("Required at least 8 characters");
            return false;
        }
        if (!txtConfirm.getText().toString().equals(txtPassword.getText().toString())){
            layoutConfirm.setErrorEnabled(true);
            layoutConfirm.setError("Password does not match");
            return false;

        }


        return true;
    }

    private void register() {
        mservice.register(txtEmail.getText().toString(),
                txtPassword.getText().toString(),
                txtName.getText().toString())
                .enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        User user=response.body();
                        /* getSuccess() return from server "wron data" something wrong  in login or register */
                        if(TextUtils.isEmpty(user.getSuccess())){
                        Common.currentUser=response.body();
                        sharedPrefManger.saveUser(response.body());
                        updateTokenToServer();
                        Toast.makeText(getApplicationContext(),"Registered Successsfully...", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, Navigation_BasedActivity.class));
                        finish();
                        }
                        else {
                            Toast.makeText(RegisterActivity.this, "Wrong data ,Maybe this email already exist...", Toast.LENGTH_SHORT).show();
                        }

                    }
                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (sharedPrefManger.isLoggedIn()){
            startActivity(new Intent(RegisterActivity.this, Navigation_BasedActivity.class));
            RegisterActivity.this.finish();
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
                                Toast.makeText(RegisterActivity.this, response.body(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });

    }

}