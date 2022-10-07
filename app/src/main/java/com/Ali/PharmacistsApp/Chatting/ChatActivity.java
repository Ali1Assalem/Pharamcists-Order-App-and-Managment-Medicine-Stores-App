package com.Ali.PharmacistsApp.Chatting;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Toast;

import com.Ali.PharmacistsApp.Chatting.Adapters.ChatAdapter;
import com.Ali.PharmacistsApp.Chatting.Models.ChatMessage;
import com.Ali.PharmacistsApp.Chatting.Models.User;

import com.Ali.PharmacistsApp.Chatting.Network.ApiClient;
import com.Ali.PharmacistsApp.Chatting.Network.Apiservice;
import com.Ali.PharmacistsApp.Chatting.Utilities.Constatnts;
import com.Ali.PharmacistsApp.Chatting.Utilities.PreferenceManager;
import com.Ali.PharmacistsApp.databinding.ActivityChatBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.jar.JarException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends BaseActivity {

    private ActivityChatBinding binding;
    private User reciverUser;
    private List<ChatMessage> chatMessages;
    private ChatAdapter chatAdapter;
    private PreferenceManager preferenceManager;
    private FirebaseFirestore database;
    private String conversationId=null;
    private Boolean isReceiverAvailable=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();
        setListeners();
        loadReciverDetails();
        init();
        listenMessages();

    }

    private void init(){
        preferenceManager=new PreferenceManager(getApplicationContext());
        chatMessages=new ArrayList<>();
        chatAdapter=new ChatAdapter(
                chatMessages,
                getBitmapFromEncodedString(reciverUser.image),
                preferenceManager.getString(Constatnts.KEY_USER_ID)
        );
        binding.chatRecyclerView.setAdapter(chatAdapter);
        database=FirebaseFirestore.getInstance();
    }

    private void sendMessage(){
        HashMap<String,Object> message=new HashMap<>();
        message.put(Constatnts.KEY_SENDER_ID,preferenceManager.getString(Constatnts.KEY_USER_ID));
        message.put(Constatnts.KEY_RECEIVER_ID,reciverUser.id);
        message.put(Constatnts.KEY_MESSAGE,binding.inputMessage.getText().toString());
        message.put(Constatnts.KEY_TIMESTAMP,new Date());

        database.collection(Constatnts.KEY_COLLECTION_CHAT).add(message);

        if (conversationId != null){
            updateConversation(binding.inputMessage.getText().toString());
        }else {
            HashMap<String,Object> conversation=new HashMap<>();
            conversation.put(Constatnts.KEY_SENDER_ID,preferenceManager.getString(Constatnts.KEY_USER_ID));
            conversation.put(Constatnts.KEY_SENDER_NAME,preferenceManager.getString(Constatnts.KEY_Name));
            conversation.put(Constatnts.KEY_SENDER_IMAGE,preferenceManager.getString(Constatnts.KEY_IMAGE));
            conversation.put(Constatnts.KEY_RECEIVER_ID,reciverUser.id);
            conversation.put(Constatnts.KEY_RECEIVER_NAME,reciverUser.name);
            conversation.put(Constatnts.KEY_RECEIVER_IMAGE,reciverUser.image);
            conversation.put(Constatnts.KEY_LAST_MEASSAGE,binding.inputMessage.getText().toString());
            conversation.put(Constatnts.KEY_TIMESTAMP,new Date());
            addConversation(conversation);
        }

        if (!isReceiverAvailable){
            try {
               JSONArray tokens=new JSONArray();
               tokens.put(reciverUser.token);

               JSONObject data=new JSONObject();
               data.put(Constatnts.KEY_USER_ID,preferenceManager.getString(Constatnts.KEY_USER_ID));
               data.put(Constatnts.KEY_Name,preferenceManager.getString(Constatnts.KEY_Name));
               data.put(Constatnts.KEY_FCM_TOKEN,preferenceManager.getString(Constatnts.KEY_FCM_TOKEN));
               data.put(Constatnts.KEY_MESSAGE,binding.inputMessage.getText().toString());

               JSONObject body=new JSONObject();
               body.put(Constatnts.REMOTE_MSG_DATA,data);
               body.put(Constatnts.REMOTE_MSG_REGISTIRATION_IDS,tokens);

               sendNotification(body.toString());

            }catch (Exception exception){
                showToast(exception.getMessage());
            }
        }
        binding.inputMessage.setText(null);
    }

    private void showToast(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void sendNotification(String messageBody){
        ApiClient.getClient().create(Apiservice.class).sendMessage(
                Constatnts.getRemoteMsgHeaders(),
                messageBody
        ).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()){
                    try {
                        if (response.body() != null) {
                            JSONObject responseJson = new JSONObject(response.body());
                            JSONArray results=responseJson.getJSONArray("results");
                            if (responseJson.getInt("failure") == 1){
                                JSONObject error=(JSONObject) results.get(0);
                                showToast(error.getString("error"));
                                return;
                            }

                        }
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                    showToast("Notification sent successfully");
                }else {
                    showToast("Error: "+response.body());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                showToast(t.getMessage());
            }
        });
    }


    private void listenAvailabilityOfReceiver(){
        database.collection(Constatnts.KEY_COLLECTION_USERS).document(
                reciverUser.id
        ).addSnapshotListener(ChatActivity.this,(value, error) -> {
            if (error != null){
                return;
            }
            if (value != null){
                if (value.getLong(Constatnts.KEY_AVAILABILITY)!= null){
                    int availability= Objects.requireNonNull(
                            value.getLong(Constatnts.KEY_AVAILABILITY)
                    ).intValue();
                    isReceiverAvailable=availability==1;
                }
                reciverUser.token=value.getString(Constatnts.KEY_FCM_TOKEN);
                if (reciverUser.image == null){
                    reciverUser.image=value.getString(Constatnts.KEY_IMAGE);
                    chatAdapter.setReciverProfileImage(getBitmapFromEncodedString(reciverUser.image));
                    chatAdapter.notifyItemRangeInserted(0,chatMessages.size());
                }
            }
            if (isReceiverAvailable){
                binding.textAvailability.setVisibility(View.VISIBLE);
            }else {
                binding.textAvailability.setVisibility(View.GONE);
            }
        });
    }

    private void listenMessages(){
        database.collection(Constatnts.KEY_COLLECTION_CHAT)
                .whereEqualTo(Constatnts.KEY_SENDER_ID,preferenceManager.getString(Constatnts.KEY_USER_ID))
                .whereEqualTo(Constatnts.KEY_RECEIVER_ID,reciverUser.id)
                .addSnapshotListener(eventListener);

        database.collection(Constatnts.KEY_COLLECTION_CHAT)
                .whereEqualTo(Constatnts.KEY_SENDER_ID,reciverUser.id)
                .whereEqualTo(Constatnts.KEY_RECEIVER_ID,preferenceManager.getString(Constatnts.KEY_USER_ID))
                .addSnapshotListener(eventListener);
    }


    private final EventListener<QuerySnapshot> eventListener=(value, error) ->{
        if (error !=null){
            return;
        }
        if(value !=null){
            int count=chatMessages.size();
            for (DocumentChange documentChange : value.getDocumentChanges()){
                if (documentChange.getType() == DocumentChange.Type.ADDED){
                    ChatMessage chatMessage=new ChatMessage();
                    chatMessage.senderId=documentChange.getDocument().getString(Constatnts.KEY_SENDER_ID);
                    chatMessage.receiverId=documentChange.getDocument().getString(Constatnts.KEY_RECEIVER_ID);
                    chatMessage.message=documentChange.getDocument().getString(Constatnts.KEY_MESSAGE);
                    chatMessage.dateTime=getReadableDateTime(documentChange.getDocument().getDate(Constatnts.KEY_TIMESTAMP));
                    chatMessage.dateObject=documentChange.getDocument().getDate(Constatnts.KEY_TIMESTAMP);

                    chatMessages.add(chatMessage);
                }
            }
            Collections.sort(chatMessages,(obj1,obj2) -> obj1.dateObject.compareTo(obj2.dateObject));
            if (count == 0){
                chatAdapter.notifyDataSetChanged();
            }else {
                chatAdapter.notifyItemRangeInserted(chatMessages.size(),chatMessages.size());
                binding.chatRecyclerView.smoothScrollToPosition(chatMessages.size() -1 );
            }
            binding.chatRecyclerView.setVisibility(View.VISIBLE);
        }
        binding.progressBar.setVisibility(View.GONE);
        if (conversationId == null){
            checkForConversation();
        }
    };




    private Bitmap getBitmapFromEncodedString(String encodedImage){
        if(encodedImage != null){
            byte[] bytes= Base64.decode(encodedImage,Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        }else {
            return null;
        }
    }


    private void loadReciverDetails(){
        reciverUser=(User) getIntent().getSerializableExtra(Constatnts.KEY_USER);
        binding.textName.setText(reciverUser.name);
    }

    private void setListeners(){
        binding.imageBack.setOnClickListener(v->onBackPressed());
        binding.layoutSend.setOnClickListener(v->sendMessage());
    }

    private String getReadableDateTime(Date date){
        return new SimpleDateFormat("MMMM dd, yyyy - hh:mm a", Locale.getDefault()).format(date);
    }

    private void addConversation(HashMap<String,Object> conversation){
        database.collection(Constatnts.KEY_COLLECTION_CONVERSATIONS)
                .add(conversation)
                .addOnSuccessListener(documentReference -> conversationId=documentReference.getId());
    }

    private void updateConversation(String message){
        DocumentReference documentReference=
                database.collection(Constatnts.KEY_COLLECTION_CONVERSATIONS)
                .document(conversationId);

        documentReference.update(
                Constatnts.KEY_LAST_MEASSAGE,message,
                Constatnts.KEY_TIMESTAMP,new Date()
        );
    }

    private void checkForConversation(){
        if (chatMessages.size() !=0 ){
            checkForConversationRemotely(
                    preferenceManager.getString(Constatnts.KEY_USER_ID),
                    reciverUser.id
            );
            checkForConversationRemotely(
                    reciverUser.id,
                    preferenceManager.getString(Constatnts.KEY_USER_ID)
            );
        }
    }

    private void checkForConversationRemotely(String senderId,String receiverId){
        database.collection(Constatnts.KEY_COLLECTION_CONVERSATIONS)
                .whereEqualTo(Constatnts.KEY_SENDER_ID,senderId)
                .whereEqualTo(Constatnts.KEY_RECEIVER_ID,receiverId)
                .get()
                .addOnCompleteListener(conversationOnCompleteListener);
    }

    private final OnCompleteListener<QuerySnapshot> conversationOnCompleteListener=task -> {
      if (task.isSuccessful() && task.getResult()!=null &&
      task.getResult().getDocuments().size() > 0){
          DocumentSnapshot documentSnapshot=task.getResult().getDocuments().get(0);
          conversationId=documentSnapshot.getId();
      }
    };

    @Override
    protected void onResume() {
        super.onResume();
        listenAvailabilityOfReceiver();
    }
}