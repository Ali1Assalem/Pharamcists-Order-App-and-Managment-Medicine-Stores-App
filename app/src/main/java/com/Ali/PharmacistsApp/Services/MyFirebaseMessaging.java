package com.Ali.PharmacistsApp.Services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


import com.Ali.PharmacistsApp.Chatting.ChatActivity;
import com.Ali.PharmacistsApp.Chatting.Models.User;
import com.Ali.PharmacistsApp.Chatting.Utilities.Constatnts;
import com.Ali.PharmacistsApp.R;
import com.Ali.PharmacistsApp.Retrofit.Api;

import com.Ali.PharmacistsApp.Utils.Common;
import com.Ali.PharmacistsApp.Utils.NotificationHelper;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyFirebaseMessaging extends FirebaseMessagingService {

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        if (Common.currentUser!=null)
            updateTokenToServer(token);
    }
    private void updateTokenToServer(String token) {
        Api mService= Common.getApi();
        mService.updateToken(Common.currentUser.getEmail(),token)
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Toast.makeText(MyFirebaseMessaging.this, response.body(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(MyFirebaseMessaging.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if (remoteMessage.getData().get(Constatnts.KEY_USER_ID)!=null) {
            sendNotificationChatting(remoteMessage);
        }else {
            if (remoteMessage.getData() != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                    sendNotificationAPI26(remoteMessage);
                else
                    sendNotifcation(remoteMessage);
            }
        }
    }

    private void sendNotificationChatting(RemoteMessage remoteMessage){
            User user = new User();
            user.id=remoteMessage.getData().get(Constatnts.KEY_USER_ID);
            user.name=remoteMessage.getData().get(Constatnts.KEY_Name);
            user.token=remoteMessage.getData().get(Constatnts.KEY_FCM_TOKEN);

            int notificationId=new Random().nextInt();
            String channelId="chat_message";

            Intent intent=new Intent(this, ChatActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.putExtra(Constatnts.KEY_USER,user);
            PendingIntent pendingIntent=PendingIntent.getActivity(this,0,intent,0);

            NotificationCompat.Builder builder=new NotificationCompat.Builder(this,channelId);
            builder.setSmallIcon(R.mipmap.ic_launcher);
            builder.setContentTitle(user.name);
            builder.setContentText(remoteMessage.getData().get(Constatnts.KEY_MESSAGE));
            builder.setStyle(new NotificationCompat.BigTextStyle().bigText(
                    remoteMessage.getData().get(Constatnts.KEY_MESSAGE)
            ));
            builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
            builder.setContentIntent(pendingIntent);
            builder.setAutoCancel(true);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                CharSequence channelName="Chat Message";
                String channelDescription="This notification channel is used for chat message notification";
                int importance=NotificationManager.IMPORTANCE_DEFAULT;
                NotificationChannel channel=new NotificationChannel(channelId,channelName,importance);
                channel.setDescription(channelDescription);
                NotificationManager notificationManager = getSystemService(NotificationManager.class);
                notificationManager.createNotificationChannel(channel);
            }

            NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(this);
            notificationManagerCompat.notify(notificationId,builder.build());
        }


    private void sendNotifcation(RemoteMessage remoteMessage) {
        //GET INFORMATION FROM MESSAGE
        Map<String,String> data=remoteMessage.getData();
        String title=data.get("title");
        String message=data.get("message");

        Uri defultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defultSoundUri);


        NotificationManager noti=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        noti.notify(new Random().nextInt(),builder.build());
    }

    private void sendNotificationAPI26(RemoteMessage remoteMessage) {
        //From Api level 26 , we need implement Notification Channel
        Map<String,String> data=remoteMessage.getData();
        String title=data.get("title");
        String message=data.get("massage");

        NotificationHelper helper;
        Notification.Builder builder;

        Uri defultSoundUri=RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        helper=new NotificationHelper(this);
        builder=helper.getDrinkShopNotification(title,message,defultSoundUri);

        helper.getManager().notify(new Random().nextInt(),builder.build());

    }

}
