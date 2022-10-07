package com.Ali.PharmacistsApp.Chatting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.Ali.PharmacistsApp.Chatting.Utilities.Constatnts;
import com.Ali.PharmacistsApp.Chatting.Utilities.PreferenceManager;
import com.Ali.PharmacistsApp.R;
import com.Ali.PharmacistsApp.databinding.ActivityChSignInBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;


public class ch_SignInActivity extends AppCompatActivity {

    private ActivityChSignInBinding binding;
    private PreferenceManager preferenceManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityChSignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();
        preferenceManager=new PreferenceManager(getApplicationContext());
        if (preferenceManager.getBoolean(Constatnts.KEY_IS_SIGNED_IN)){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

        setListeners();
    }
    private void setListeners() {
        binding.textCreateNewAccount.setOnClickListener(v->{
            startActivity(new Intent(getApplicationContext(),ch_SignUpActivity.class));
            finish();
        });
        binding.buttonSignIn.setOnClickListener(v->{
            if (isValidSignInDetails()){
                signIn();
            }
        });
    }

    private void signIn() {
        loading(true);
        FirebaseFirestore database=FirebaseFirestore.getInstance();
        database.collection(Constatnts.KEY_COLLECTION_USERS)
                .whereEqualTo(Constatnts.KEY_EMAIL,binding.inputEmail.getText().toString())
                .whereEqualTo(Constatnts.KEY_PASSWORD,binding.inputPassword.getText().toString())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful() && task.getResult() != null
                        && task.getResult().getDocuments().size() >0 ){
                            DocumentSnapshot documentSnapshot=task.getResult().getDocuments().get(0);
                            preferenceManager.putBoolean(Constatnts.KEY_IS_SIGNED_IN,true);
                            preferenceManager.putString(Constatnts.KEY_USER_ID,documentSnapshot.getId());
                            preferenceManager.putString(Constatnts.KEY_Name,documentSnapshot.getString(Constatnts.KEY_Name));
                            preferenceManager.putString(Constatnts.KEY_EMAIL,documentSnapshot.getString(Constatnts.KEY_EMAIL));
                            preferenceManager.putString(Constatnts.KEY_IMAGE,documentSnapshot.getString(Constatnts.KEY_IMAGE));

                            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                            //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();

                        }else {
                            loading(false);
                            showToast("Unable to Sign In");
                        }
                    }
                });
    }

    private void loading(Boolean isLoading){
        if (isLoading){
            binding.buttonSignIn.setVisibility(View.INVISIBLE);
            binding.progressBar.setVisibility(View.VISIBLE);
        }else {
            binding.buttonSignIn.setVisibility(View.VISIBLE);
            binding.progressBar.setVisibility(View.INVISIBLE);
        }
    }


    private void showToast(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private Boolean isValidSignInDetails(){
        if (binding.inputEmail.getText().toString().trim().isEmpty()){
            showToast("Please Enter Email");
            return false;
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(binding.inputEmail.getText().toString()).matches()){
            showToast("please Enter Valid Email");
            return false;
        }
        else if (binding.inputPassword.getText().toString().trim().isEmpty()){
            showToast("please Enter Password ");
            return false;
        }
        else {
            return true;
        }
    }
}