package com.Ali.PharmacistsApp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.Ali.PharmacistsApp.Interface.UploadCallBack;
import com.Ali.PharmacistsApp.Model.Image;
import com.Ali.PharmacistsApp.Retrofit.Api;
import com.Ali.PharmacistsApp.Utils.Common;
import com.Ali.PharmacistsApp.Utils.ProgressRequestBody;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.Ali.PharmacistsApp.databinding.ActivityBasedNavigationBinding;
import com.google.firebase.messaging.FirebaseMessaging;
import com.nex3z.notificationbadge.NotificationBadge;
import com.squareup.picasso.Picasso;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;
import edmt.dev.afilechooser.utils.FileUtils;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Navigation_BasedActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, UploadCallBack{
    private static final int PICK_FILE_REQUEST = 1222;
    private static final int REQUEST_PERMISSION = 1001;
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityBasedNavigationBinding binding;
    TextView textName,textEmail;
    CircleImageView img_avatar;
    Uri selectedFileUri;

    Api mservice;
    static NotificationBadge badge;
    ImageView cart_icon;

    SharedPrefManger sharedPrefManger;

        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            switch (requestCode) {
                case REQUEST_PERMISSION: {
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                        Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //getSupportActionBar().setTitle("Hi "+Common.currentUser.getName());

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE
            }, REQUEST_PERMISSION);

        binding = ActivityBasedNavigationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.


        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_favorite,R.id.nav_setting, R.id.nav_logout)
                .setOpenableLayout(drawer)
                .build();


        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        View headerView=navigationView.getHeaderView(0);
        textName=headerView.findViewById(R.id.textName);
        textEmail=headerView.findViewById(R.id.textEmail);
        img_avatar=headerView.findViewById(R.id.imageView);

        mservice=Common.getApi();

        //initillize sharedprefrerences to upadete user profile image
        sharedPrefManger=new SharedPrefManger(Navigation_BasedActivity.this);
        Common.currentUser=sharedPrefManger.getUser();
        //set Avatar

        if(!TextUtils.isEmpty(sharedPrefManger.getImage(getApplicationContext()))) {
            Picasso.get()
                    .load(Common.BASE_URL_IMAGE +
                            sharedPrefManger.getImage(getApplicationContext()))
                    .into(img_avatar);
        }


        textName.setText(Common.currentUser.getName());
        textEmail.setText(Common.currentUser.getEmail());


        img_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });

        //updateTokenToServer();
        }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_action_bar, menu);
        View view=menu.findItem(R.id.cart_menu).getActionView();
        badge= (NotificationBadge) view.findViewById(R.id.badge);
        cart_icon=view.findViewById(R.id.cart_icon);

        cart_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity( new Intent(Navigation_BasedActivity.this, CartActivity.class));
            }
        });

        updateCartCount();
        return true;
    }

    public static void updateCartCount() {
        if (badge == null) return;

        if (Common.cartRepository.countCartItems() == 0) {
            badge.setVisibility(View.INVISIBLE);
        } else {
            badge.setVisibility(View.VISIBLE);
            badge.setText(String.valueOf(Common.cartRepository.countCartItems()));
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id=item.getItemId();
        if (id==R.id.cart_menu){
            return true;
        }else if (id==R.id.search_menu){
            startActivity(new Intent(Navigation_BasedActivity.this,SearchActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }





    @Override
    protected void onResume() {
        super.onResume();
        //update info after back from update activity
        textName.setText(Common.currentUser.getName());
        textEmail.setText(Common.currentUser.getEmail());
        updateCartCount();

        updateTokenToServer();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if (id==R.id.nav_favorite)
        {
            startActivity(new Intent(Navigation_BasedActivity.this, CartActivity.class));
        }

        else if (id==R.id.nav_home)
        {
            //startActivity(new Intent(HomeActivity.this, NearbyStoreActivity.class));
        }
        else if (id==R.id.nav_order)
        {
            startActivity(new Intent(Navigation_BasedActivity.this,OrderActivity.class));
        }
        else if (id==R.id.nav_setting)
        {
            startActivity(new Intent(Navigation_BasedActivity.this,SettingActivity.class));

        }
        else if (item.getItemId()==R.id.nav_logout) {

        }
        DrawerLayout drawer=findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



        private void chooseImage() {
        startActivityForResult(Intent.createChooser(FileUtils.createGetContentIntent(),"Select a file"),
                PICK_FILE_REQUEST);
    }



        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if(resultCode== Activity.RESULT_OK)
            {
                if(requestCode==PICK_FILE_REQUEST)
                {
                    if(data!=null)
                    {
                        selectedFileUri=data.getData();
                        if(selectedFileUri!=null && !selectedFileUri.getPath().isEmpty())
                        {
                            img_avatar.setImageURI(selectedFileUri);
                            uploadFile();
                        }
                        else
                        {
                            Toast.makeText(this, "Cannot upload file to server", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        }


        private void uploadFile() {
            if(selectedFileUri!=null)
            {
                File file= FileUtils.getFile(this,selectedFileUri);

                String fileName=new StringBuilder(Common.currentUser.getEmail())
                        .append(FileUtils.getExtension(file.toString()))
                        .toString();


                ProgressRequestBody requestFile= new ProgressRequestBody(file,this);

                final MultipartBody.Part body=MultipartBody.Part.createFormData("image",fileName,requestFile);
                final MultipartBody.Part userEmail=MultipartBody.Part.createFormData("email",Common.currentUser.getEmail());

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mservice.uploadFile(userEmail,body)
                                .enqueue(new Callback<Image>() {
                                    @Override
                                    public void onResponse(Call<Image> call, Response<Image> response) {
                                        Image image=response.body();
                                        assert image != null;
                                        String imagePath=image.getPath();
                                        sharedPrefManger.setImage(imagePath);
                                        //update currentuser info with new image
                                        Common.currentUser=sharedPrefManger.getUser();
                                        Toast.makeText(Navigation_BasedActivity.this, image.getSuccess(), Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onFailure(Call<Image> call, Throwable t) {
                                        Toast.makeText(Navigation_BasedActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                }).start();
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
                                Toast.makeText(Navigation_BasedActivity.this, response.body(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                Toast.makeText(Navigation_BasedActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });

    }

        @Override
        public void onProgressUpdate(int pertantage) {

        }




}