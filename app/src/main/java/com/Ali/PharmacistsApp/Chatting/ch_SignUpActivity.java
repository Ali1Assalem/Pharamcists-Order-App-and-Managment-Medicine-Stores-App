package com.Ali.PharmacistsApp.Chatting;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.Ali.PharmacistsApp.Chatting.Utilities.Constatnts;
import com.Ali.PharmacistsApp.Chatting.Utilities.PreferenceManager;
import com.Ali.PharmacistsApp.Navigation_BasedActivity;
import com.Ali.PharmacistsApp.R;
import com.Ali.PharmacistsApp.Utils.Common;
import com.Ali.PharmacistsApp.databinding.ActivityChSignUpBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.regex.Pattern;

public class ch_SignUpActivity extends AppCompatActivity {

    private ActivityChSignUpBinding binding;
    private PreferenceManager preferenceManager;
    private String encodedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityChSignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();
        preferenceManager=new PreferenceManager(getApplicationContext());
        setListeners();
    }

    private void setListeners() {
        binding.textSignIn.setOnClickListener(v-> {
            startActivity(new Intent(getApplicationContext(), ch_SignInActivity.class));
            finish();
        });
        binding.buttonSignUp.setOnClickListener(v->{
            if (isValidSignInDetails())
                signUp();
        });
        binding.layoutImage.setOnClickListener(v->{
            Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            pickImage.launch(intent);
        });
    }

    private void signUp() {
        loading(true);
        FirebaseFirestore database=FirebaseFirestore.getInstance();
        HashMap<String,Object> user=new HashMap<>();
        user.put(Constatnts.KEY_Name,binding.inputName.getText().toString());
        user.put(Constatnts.KEY_EMAIL,binding.inputEmail.getText().toString());
        user.put(Constatnts.KEY_PASSWORD,binding.inputPassword.getText().toString());
        user.put(Constatnts.KEY_IMAGE,encodedImage);

        database.collection(Constatnts.KEY_COLLECTION_USERS)
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        loading(false);
                        preferenceManager.putBoolean(Constatnts.KEY_IS_SIGNED_IN,true);
                        preferenceManager.putString(Constatnts.KEY_USER_ID,documentReference.getId());
                        preferenceManager.putString(Constatnts.KEY_Name,binding.inputName.getText().toString());
                        preferenceManager.putString(Constatnts.KEY_IMAGE,encodedImage);

                        Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                        //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        loading(false);
                        showToast(e.getMessage());
                    }
                });
    }

    private String encodeImage(Bitmap bitmap){
        int previewWidth=150;
        int previewHeight=bitmap.getHeight()*previewWidth / bitmap.getWidth();
        Bitmap previewBitmap=Bitmap.createScaledBitmap(bitmap,previewWidth,previewHeight,false);
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        previewBitmap.compress(Bitmap.CompressFormat.JPEG,50,byteArrayOutputStream);
        byte[] bytes=byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(bytes,Base64.DEFAULT);
    }


    private final ActivityResultLauncher<Intent> pickImage=registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    if (result.getData()!=null){
                        Uri imageUri=result.getData().getData();
                        try {
                            InputStream inputStream=getContentResolver().openInputStream(imageUri);
                            Bitmap bitmap= BitmapFactory.decodeStream(inputStream);
                            binding.imageProfile.setImageBitmap(bitmap);
                            binding.textAddImage.setVisibility(View.GONE);
                            encodedImage=encodeImage(bitmap);

                        }catch (FileNotFoundException e){
                            e.printStackTrace();
                        }
                    }
                }
            }
    );

    private void showToast(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private Boolean isValidSignInDetails(){
        if (encodedImage == null){
            showToast("Select profile image");
            return false;
        }
//        else if(!binding.inputName.getText().toString().equals(Common.currentUser.getName())){
//            showToast("Plese insert the same account name");
//            return false;
//        }
        else if (binding.inputName.getText().toString().trim().isEmpty()){
            showToast("Please Enter Name");
            return false;
        }
//        else if (!binding.inputEmail.getText().toString().equals(Common.currentUser.getEmail())){
//            showToast("Plese insert the same account email");
//            return false;
//        }
        else if (binding.inputEmail.getText().toString().trim().isEmpty()){
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
        else if (binding.inputConfirmPassword.getText().toString().trim().isEmpty()){
            showToast("please confirm password");
            return false;
        }
        else if (!binding.inputConfirmPassword.getText().toString().equals(binding.inputPassword.getText().toString())){
            showToast("Password & Confirm password must be same...");
            return false;
        }
        else {
            return true;
        }
    }

    private void loading(Boolean isLoading){
        if (isLoading){
            binding.buttonSignUp.setVisibility(View.INVISIBLE);
            binding.progressBar.setVisibility(View.VISIBLE);
        }else {
            binding.buttonSignUp.setVisibility(View.VISIBLE);
            binding.progressBar.setVisibility(View.INVISIBLE);
        }
    }

}