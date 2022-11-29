package com.example.esii;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.FileNotFoundException;

public class PostActivity extends AppCompatActivity {

    private ImageView postImg;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        getSupportActionBar().hide();

        postImg= findViewById(R.id.post);
        Uri targetUri = (Uri) getIntent().getExtras().get("post");
        try {
            bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        postImg.setImageBitmap(bitmap);

    }
}