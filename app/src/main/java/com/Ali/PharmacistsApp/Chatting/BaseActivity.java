package com.Ali.PharmacistsApp.Chatting;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.Ali.PharmacistsApp.Chatting.Utilities.Constatnts;
import com.Ali.PharmacistsApp.Chatting.Utilities.PreferenceManager;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class BaseActivity extends AppCompatActivity {
    private DocumentReference documentReference;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PreferenceManager preferenceManager=new PreferenceManager(getApplicationContext());
        FirebaseFirestore database=FirebaseFirestore.getInstance();
        documentReference=database.collection(Constatnts.KEY_COLLECTION_USERS)
                .document(preferenceManager.getString(Constatnts.KEY_USER_ID));

    }

    @Override
    protected void onPause() {
        super.onPause();
        documentReference.update(Constatnts.KEY_AVAILABILITY,0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        documentReference.update(Constatnts.KEY_AVAILABILITY,1);
    }
}
