package com.example.esii;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;

public class AddPictureActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView gallery;
    private ImageView camera;
    private ActivityResultLauncher<Intent> addPictureActivityResultLauncher;
    private ActivityResultLauncher<Intent> takePictureResultLauncher;
    private  Bitmap bitmap;

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_picture);
        getSupportActionBar().hide();

        gallery= findViewById(R.id.galleryPost);
        camera= findViewById(R.id.cameraPost);

        gallery.setOnClickListener(this);
        camera.setOnClickListener(this);

        addPictureActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            // There are no request codes
                            Intent data = result.getData();
                            Uri targetUri = data.getData();
                            Intent i2= new Intent (AddPictureActivity.this, PostActivity.class);
                            i2.putExtra("post", targetUri);
                            startActivity(i2);

                        }
                    }
                });

        takePictureResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            // There are no request codes
                            Intent data = result.getData();
                            Bitmap photo = (Bitmap) data.getExtras().get("data");
                            Uri targetUri = getImageUri(AddPictureActivity.this, photo);

                            Intent i3= new Intent (AddPictureActivity.this, PostActivity.class);
                            i3.putExtra("post", targetUri);
                            startActivity(i3);

                        }
                    }
                });

        bottomNavigationView = findViewById(R.id.bottom_navbar);
        bottomNavigationView.setSelectedItemId(R.id.cont);

        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.chat:
                        startActivity(new Intent(getApplicationContext(), ChatActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.cont:

                        return true;

                    case R.id.courses:
                        startActivity(new Intent(getApplicationContext(), CoursesActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.galleryPost:
                Intent iPost=new Intent (Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                addPictureActivityResultLauncher.launch(iPost);
                break;
            case R.id.cameraPost:
                Intent iCam  = new Intent (MediaStore.ACTION_IMAGE_CAPTURE);
                takePictureResultLauncher.launch(iCam);
                break;
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
}