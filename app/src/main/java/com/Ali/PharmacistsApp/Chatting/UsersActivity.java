package com.Ali.PharmacistsApp.Chatting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.Ali.PharmacistsApp.Chatting.Adapters.UsersAdapter;
import com.Ali.PharmacistsApp.Chatting.Listeners.UserListener;
import com.Ali.PharmacistsApp.Chatting.Models.User;
import com.Ali.PharmacistsApp.Chatting.Utilities.Constatnts;
import com.Ali.PharmacistsApp.Chatting.Utilities.PreferenceManager;
import com.Ali.PharmacistsApp.databinding.ActivityUsersBinding;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class UsersActivity extends BaseActivity implements UserListener {

    private ActivityUsersBinding binding;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityUsersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();
        preferenceManager=new PreferenceManager(getApplicationContext());
        setListeners();
        getUsers();
    }

    private void setListeners(){
        binding.imageBack.setOnClickListener(e->onBackPressed());
    }

    private void getUsers(){
        loading(true);
        FirebaseFirestore database =FirebaseFirestore.getInstance();
        database.collection(Constatnts.KEY_COLLECTION_USERS)
                .get()
                .addOnCompleteListener(task -> {
                   loading(false);
                   String currentUserId=preferenceManager.getString(Constatnts.KEY_USER_ID);
                   if (task.isSuccessful() && task.getResult() != null){
                       List<User> users=new ArrayList<>();
                       for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()){
                           if (currentUserId.equals(queryDocumentSnapshot.getId())){
                               continue;
                           }
                           User user=new User();
                           user.name=queryDocumentSnapshot.getString(Constatnts.KEY_Name);
                           user.email=queryDocumentSnapshot.getString(Constatnts.KEY_EMAIL);
                           user.image=queryDocumentSnapshot.getString(Constatnts.KEY_IMAGE);
                           user.token=queryDocumentSnapshot.getString(Constatnts.KEY_FCM_TOKEN);
                           user.id=queryDocumentSnapshot.getId();

                           users.add(user);

                       }
                       if (users.size() > 0){
                           UsersAdapter usersAdapter= new UsersAdapter(users,this);
                           binding.usersRecyclerView.setAdapter(usersAdapter);
                           binding.usersRecyclerView.setVisibility(View.VISIBLE);
                       }else {
                           showErrorMessage();
                       }

                   }else {
                       showErrorMessage();
                   }
                });

    }

    private void showErrorMessage(){
        binding.txetErrorMessage.setText(String.format("%s","No users available"));
        binding.txetErrorMessage.setVisibility(View.VISIBLE);
    }

    private void loading(Boolean isLoading){
        if (isLoading){
            binding.progressBar.setVisibility(View.VISIBLE);
        }else {
            binding.progressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onUserClicked(User user) {
        Intent intent=new Intent(getApplicationContext(),ChatActivity.class);
        intent.putExtra(Constatnts.KEY_USER,user);
        startActivity(intent);
        finish();
    }
}