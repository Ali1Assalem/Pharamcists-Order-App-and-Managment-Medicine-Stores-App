package com.Ali.PharmacistsApp.Chatting;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Toast;

import com.Ali.PharmacistsApp.Chatting.Adapters.RecentConversionAdapter;
import com.Ali.PharmacistsApp.Chatting.Listeners.ConversationListener;
import com.Ali.PharmacistsApp.Chatting.Models.ChatMessage;
import com.Ali.PharmacistsApp.Chatting.Models.User;
import com.Ali.PharmacistsApp.Chatting.Utilities.Constatnts;
import com.Ali.PharmacistsApp.Chatting.Utilities.PreferenceManager;
import com.Ali.PharmacistsApp.Retrofit.Api;
import com.Ali.PharmacistsApp.Utils.Common;
import com.Ali.PharmacistsApp.databinding.ActivityMainBinding;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity implements ConversationListener {

    private ActivityMainBinding binding;
    private PreferenceManager preferenceManager;
    private List<ChatMessage> conversations;
    private RecentConversionAdapter conversionsAdapter;
    private FirebaseFirestore database;

    Api mservice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mservice=Common.getApi();

        preferenceManager=new PreferenceManager(getApplicationContext());
        init();
        loadUserDetails();
        getToken();
        setListeners();
        listenConversations();
    }

    private void init() {
        conversations=new ArrayList<>();
        conversionsAdapter=new RecentConversionAdapter(conversations,this);
        binding.conversionsRecyclerView.setAdapter(conversionsAdapter);
        database=FirebaseFirestore.getInstance();
    }

    private void setListeners() {
        binding.imageSignOut.setOnClickListener(v -> signOut());
        binding.fabNewChat.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                 startActivity(new Intent(getApplicationContext(),UsersActivity.class));
                    }
                }
        );
    }

    private void loadUserDetails(){
        binding.textName.setText(preferenceManager.getString(Constatnts.KEY_Name));
        byte[] bytes= Base64.decode(preferenceManager.getString(Constatnts.KEY_IMAGE),Base64.DEFAULT);
        Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        binding.imageProfile.setImageBitmap(bitmap);
    }

    private void showToast(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }


    private void listenConversations(){
        database.collection(Constatnts.KEY_COLLECTION_CONVERSATIONS)
                .whereEqualTo(Constatnts.KEY_SENDER_ID,preferenceManager.getString(Constatnts.KEY_USER_ID))
                .addSnapshotListener(eventListener);

        database.collection(Constatnts.KEY_COLLECTION_CONVERSATIONS)
                .whereEqualTo(Constatnts.KEY_RECEIVER_ID,preferenceManager.getString(Constatnts.KEY_USER_ID))
                .addSnapshotListener(eventListener);
    }


    private final EventListener<QuerySnapshot> eventListener=(value, error) -> {
        if (error != null){
            return;
        }
        if (value != null){
            for (DocumentChange documentChange: value.getDocumentChanges()){
                if (documentChange.getType() ==DocumentChange.Type.ADDED){
                    String senderId=documentChange.getDocument().getString(Constatnts.KEY_SENDER_ID);
                    String receiverId=documentChange.getDocument().getString(Constatnts.KEY_RECEIVER_ID);
                    ChatMessage chatMessage=new ChatMessage();
                    chatMessage.senderId = senderId;
                    chatMessage.receiverId=receiverId;
                    if (preferenceManager.getString(Constatnts.KEY_USER_ID).equals(senderId)){
                        chatMessage.conversionImage=documentChange.getDocument().getString(Constatnts.KEY_RECEIVER_IMAGE);
                        chatMessage.conversionName=documentChange.getDocument().getString(Constatnts.KEY_RECEIVER_NAME);
                        chatMessage.conversionId=documentChange.getDocument().getString(Constatnts.KEY_RECEIVER_ID);
                    }else {
                        chatMessage.conversionImage=documentChange.getDocument().getString(Constatnts.KEY_SENDER_IMAGE);
                        chatMessage.conversionName=documentChange.getDocument().getString(Constatnts.KEY_SENDER_NAME);
                        chatMessage.conversionId=documentChange.getDocument().getString(Constatnts.KEY_SENDER_ID);
                    }
                    chatMessage.message=documentChange.getDocument().getString(Constatnts.KEY_LAST_MEASSAGE);
                    chatMessage.dateObject=documentChange.getDocument().getDate(Constatnts.KEY_TIMESTAMP);
                    conversations.add(chatMessage);
                }else if (documentChange.getType() == DocumentChange.Type.MODIFIED){
                    for (int i=0;i<conversations.size();i++){
                        String senderId=documentChange.getDocument().getString(Constatnts.KEY_SENDER_ID);
                        String receiverId=documentChange.getDocument().getString(Constatnts.KEY_RECEIVER_ID);
                        if (conversations.get(i).senderId.equals(senderId) && conversations.get(i).receiverId.equals(receiverId)){
                            conversations.get(i).message = documentChange.getDocument().getString(Constatnts.KEY_LAST_MEASSAGE);
                            conversations.get(i).dateObject = documentChange.getDocument().getDate(Constatnts.KEY_TIMESTAMP);
                            break;
                        }
                    }
                }
            }
            Collections.sort(conversations,(obj1,obj2)->obj2.dateObject.compareTo(obj1.dateObject));
            conversionsAdapter.notifyDataSetChanged();
            binding.conversionsRecyclerView.smoothScrollToPosition(0);
            binding.conversionsRecyclerView.setVisibility(View.VISIBLE);
            binding.progressBar.setVisibility(View.GONE);
        }
    };

    private void getToken(){
        FirebaseMessaging.getInstance().getToken().addOnSuccessListener(this::updateToken);
    }

    private void updateToken(String token){
        preferenceManager.putString(Constatnts.KEY_FCM_TOKEN,token);
        FirebaseFirestore database=FirebaseFirestore.getInstance();
        DocumentReference documentReference=
                database.collection(Constatnts.KEY_COLLECTION_USERS).document(
                        preferenceManager.getString(Constatnts.KEY_USER_ID)
                );

        documentReference.update(Constatnts.KEY_FCM_TOKEN,token)
                .addOnSuccessListener(unused -> {
                    showToast("Token updated successfulluy");
                    updateTokenToServer(token);
                })
                .addOnFailureListener(e -> showToast("Unable to update token"));
    }

    private void signOut(){
        showToast("Signing out...");
        FirebaseFirestore database=FirebaseFirestore.getInstance();
        DocumentReference documentReference=
                database.collection(Constatnts.KEY_COLLECTION_USERS).document(
                        preferenceManager.getString(Constatnts.KEY_USER_ID)
                );

        HashMap<String,Object> updates=new HashMap<>();
        updates.put(Constatnts.KEY_FCM_TOKEN, FieldValue.delete());
        documentReference.update(updates)
                .addOnSuccessListener(unused -> {
                    preferenceManager.clear();
                    startActivity(new Intent(getApplicationContext(), ch_SignInActivity.class));
                    finish();
                }).addOnFailureListener(e -> showToast("Unable to sign out"));
    }


    private void updateTokenToServer(String token) {
        if (token != null) {
            mservice.updateToken(Common.currentUser.getEmail(), token)
                    .enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            //Toast.makeText(getApplicationContext(), response.body(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "Unable to update token with laravel serever.." + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }

    }

    @Override
    public void onConversationClicked(User user) {
        Intent intent=new Intent(getApplicationContext(),ChatActivity.class);
        intent.putExtra(Constatnts.KEY_USER,user);
        startActivity(intent);
    }
}
